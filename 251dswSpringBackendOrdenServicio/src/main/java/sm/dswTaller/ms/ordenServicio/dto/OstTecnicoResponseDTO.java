
package sm.dswTaller.ms.ordenServicio.dto;

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
    private Long idOst;
    private Long idTecnico;
    private String estado;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinalizacion;
    private String observaciones;
}