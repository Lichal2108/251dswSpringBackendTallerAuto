
package sm.dswTaller.ms.tallerAutomotriz.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioAutoRequestDTO {
    private Integer idItem;
    private Integer cantidad;
    private String estado;
}
