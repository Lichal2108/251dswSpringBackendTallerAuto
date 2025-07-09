package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.dto.PersonaResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioMDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.Rol;
import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.RolRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.UsuarioRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;


@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    PersonaRepository PersonaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    public List<UsuarioResponse> listUsuarios(){
        return UsuarioResponse.fromEntities(usuarioRepository.findAll());
        
    }
    public UsuarioResponse insertUsuario(UsuarioRequest usuarioRequest){
        Integer idRol = usuarioRequest.getIdRol();
        
        
        Rol rol = rolRepository.findById(idRol).get();
        if(rol==null) return new UsuarioResponse();
        
        Integer idPersona=usuarioRequest.getIdPersona();
        Persona persona=PersonaRepository.findById(idPersona).get();
        if(persona==null) return new UsuarioResponse();
        
        String passwordEncriptada = passwordEncoder.encode(usuarioRequest.getPassword());
        
        Usuario usuario = new Usuario(
                usuarioRequest.getIdUsuario(),
                usuarioRequest.getNombreUsuario(),
                passwordEncriptada,
                rol,
                persona
                 
        );
        usuario=usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuario);
        
        
    }
    public UsuarioResponse updateUsuario(UsuarioRequest usuarioRequest){
        Integer idRol = usuarioRequest.getIdRol();
        Rol rol = rolRepository.findById(idRol).get();
        if(rol==null) return new UsuarioResponse();
        
        Integer idPersona=usuarioRequest.getIdPersona();
        Persona persona=PersonaRepository.findById(idPersona).get();
        if(persona==null) return new UsuarioResponse();
        
        Usuario usuario = new Usuario(
                usuarioRequest.getIdUsuario(),
                usuarioRequest.getNombreUsuario(),
                usuarioRequest.getPassword(),
                rol,
                persona
                 
        );
        usuario=usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuario);
        
    }
    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
        
    }
    public UsuarioResponse findUsuario(Long id){
        Optional<Usuario> result=usuarioRepository.findById(id);
        if(!result.isPresent())
            return null;
        return UsuarioResponse.fromEntity(result.get());
        
        
    }
    
    public UsuarioMDTO getUsuarioMiniById(Long id){
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Persona persona = usuario.getPersona();
        return UsuarioMDTO.builder()
            .idUsuario(usuario.getId())
            .persona(PersonaResponse.fromEntity(persona))
            .build();
    }   
    
    public Long obtenerIdPersonaPorUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getPersona() != null) {
            return Long.valueOf(usuarioOpt.get().getPersona().getIdPersona());
        }
        return null;
    }
}
