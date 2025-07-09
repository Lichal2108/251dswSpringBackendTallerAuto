
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
public class AutoDTO {
    private Long idAuto;
    private String placa;
    private String modelo;
    private Integer anio;
    private String color;
    private PersonaDTO persona;
}
