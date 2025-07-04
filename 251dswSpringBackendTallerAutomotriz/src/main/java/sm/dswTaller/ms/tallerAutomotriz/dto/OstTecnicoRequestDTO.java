package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstTecnicoRequestDTO {
    private Integer idOst;
    private List<AsignacionTecnicoDTO> asignaciones;
}