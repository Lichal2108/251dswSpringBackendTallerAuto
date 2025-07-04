
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private Long idUsuario;
    private String nombreUsuario;
    private String password;
    private Integer idRol;
    
    private Integer idPersona;    
    
}
