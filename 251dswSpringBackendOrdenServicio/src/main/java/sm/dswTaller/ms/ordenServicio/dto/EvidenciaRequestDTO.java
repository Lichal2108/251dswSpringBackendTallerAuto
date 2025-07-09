package sm.dswTaller.ms.ordenServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldair
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaRequestDTO {
    private Long idOst;
    private Long idTecnico;
    private String descripcion;
}