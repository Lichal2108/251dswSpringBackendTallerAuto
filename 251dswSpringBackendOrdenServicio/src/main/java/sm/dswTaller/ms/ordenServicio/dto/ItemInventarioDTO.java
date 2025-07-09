package sm.dswTaller.ms.ordenServicio.dto;

/**
 *
 * @author Aldair
 */
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.ordenServicio.model.ItemInventario;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioDTO {
    private Long idItem;
    private String nombre;
    private CategoriaItemDTO categoria;
    
    public static ItemInventarioDTO fromEntity(ItemInventario inventario) {
        return ItemInventarioDTO.builder()
                .idItem(inventario.getIdItem())
                .nombre(inventario.getNombre())
                .categoria(CategoriaItemDTO.fromEntity(inventario.getCategoria()))
                .build();
    }
}
