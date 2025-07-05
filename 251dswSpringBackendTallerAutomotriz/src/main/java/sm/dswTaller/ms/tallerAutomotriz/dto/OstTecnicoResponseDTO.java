
package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstTecnicoResponseDTO {
    private Integer idOst;
    private Long idTecnico;
    private String estado;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinalizacion;
    private String observaciones;
}