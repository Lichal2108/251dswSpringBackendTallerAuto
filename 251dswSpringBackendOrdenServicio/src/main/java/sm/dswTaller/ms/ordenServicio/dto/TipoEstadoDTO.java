
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoEstadoDTO {
    private int id;
    private String estado;
}