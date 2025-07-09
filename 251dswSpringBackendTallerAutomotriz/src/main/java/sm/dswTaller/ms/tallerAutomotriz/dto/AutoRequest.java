
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoRequest {
    private Long idAuto;
    private String placa;
    private Integer idModelo;
    private Integer idPersona;
    private Integer anio;
    private String color;
    
}
