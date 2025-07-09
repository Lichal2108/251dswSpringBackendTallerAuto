package sm.dswTaller.ms.ordenServicio.dto;

/**
 *
 * @author Aldair
 */
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstTecnicoCompletoDTO {
    private OstResponseDTO ost;
    private Long idOstTecnico;
    private String estadoAsignacion;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinalizacion;
    private String observaciones;
}