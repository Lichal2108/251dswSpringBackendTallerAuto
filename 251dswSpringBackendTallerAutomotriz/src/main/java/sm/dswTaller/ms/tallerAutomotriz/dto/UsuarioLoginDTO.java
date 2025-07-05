package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioLoginDTO {
    private Long id;
    private String nombreUsuario;
    private String nombreCompleto; // tomado de Persona
    private String nroDocumento;
    private String rol;
    private String tipoDocumento;
    private String telefono;
}