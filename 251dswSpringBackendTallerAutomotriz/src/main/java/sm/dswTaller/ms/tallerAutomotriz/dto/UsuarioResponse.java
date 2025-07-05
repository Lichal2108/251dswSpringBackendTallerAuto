package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.Rol;
import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long idUsuario;
    private String nombreUsuario;
    
    private String password;
    private Rol rol;
    private Persona Persona;
    public static UsuarioResponse fromEntity(Usuario usuario){
        return UsuarioResponse.builder()
                .idUsuario(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .password(usuario.getPassword())
                .rol(usuario.getRol())
                .Persona(usuario.getPersona())
                .build();
        
    }
    public static List<UsuarioResponse> fromEntities(List<Usuario> usuario){
       return usuario.stream()
               .map(UsuarioResponse::fromEntity)
               .collect(Collectors.toList());
    }
}
