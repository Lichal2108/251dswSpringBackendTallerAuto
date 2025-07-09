package sm.dswTaller.ms.tallerAutomotriz.service;

import sm.dswTaller.ms.tallerAutomotriz.dto.AuthResponseDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.LoginRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.RegisterRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioLoginDTO;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.Rol;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoDocumento;
import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.RolRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TipoDocumentoRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.UsuarioRepository;
import sm.dswTaller.ms.tallerAutomotriz.utils.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    
    
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // checking...
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new IllegalArgumentException("Hay un usuario registrado con ese email");
        }
        if (personaRepository.existsByNroDocumento(request.getNroDocumento())) {
            throw new IllegalArgumentException("Hay un usuario registrado con ese nro de documento");
        }
        //char sexo=request.getSexo().charAt(0);
        TipoDocumento tipoDoc = tipoDocumentoRepository.findByTipoDoc(request.getTipoDocumento())
            .orElseThrow(() -> new RuntimeException("Tipo de documento no válido"));

        Persona persona = Persona.builder()
            .nroDocumento(request.getNroDocumento())
            .apellidoPaterno(request.getApellidoPaterno())
            .apellidoMaterno(request.getApellidoMaterno())
            .nombres(request.getNombres())
            .direccion(request.getDireccion())
            .sexo(request.getSexo())
            .telefono(request.getTelefono())
            .tipoDocumento(tipoDoc)
            .build();

        personaRepository.save(persona);

        Rol rol = rolRepository.findByRol("Cliente") // O el que venga del DTO
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = Usuario.builder()
            .nombreUsuario(request.getNombreUsuario())
            .password(passwordEncoder.encode(request.getPassword()))
            .persona(persona)
            .rol(rol)
            .build();

        usuarioRepository.save(usuario);


        String token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .build();
    }
    
    public AuthResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Nombre de usuario no encontrado"));
            
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Contrasena incorrecta");
        }
        
        Persona persona = usuario.getPersona();
        String rolRaw = usuario.getRol().getRol();
        String rolMapped = (rolRaw.equalsIgnoreCase("admin") || rolRaw.equalsIgnoreCase("administrador")) ? "admin" : rolRaw.toLowerCase();
        UsuarioLoginDTO dto = UsuarioLoginDTO.builder()
            .id(usuario.getId())
            .nombreUsuario(usuario.getNombreUsuario())
            .nombreCompleto(persona.getNombres() + " " + persona.getApellidoPaterno())
            .telefono(persona.getTelefono())
            .nroDocumento(persona.getNroDocumento())
            .tipoDocumento(persona.getTipoDocumento().getTipoDoc())
            .rol(rolMapped)
            .build();
        
        String token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
            .token(token)
            .usuario(dto)
            .build();
    }
}
