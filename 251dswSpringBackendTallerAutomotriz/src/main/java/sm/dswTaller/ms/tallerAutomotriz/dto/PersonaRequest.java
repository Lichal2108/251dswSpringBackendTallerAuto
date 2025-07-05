package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequest {
    private int idPersona;
    private String nroDocumento;
    private Integer idTipoDoc;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String direccion;
    private String sexo;
    private String telefono;
}
