package sm.dswTaller.ms.ordenServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.ordenServicio.model.CategoriaItem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaItemDTO {
    private Long idCategoria;
    private String nombre;
    
    public static CategoriaItemDTO fromEntity(CategoriaItem categoria) {
        return CategoriaItemDTO.builder()
               .idCategoria(categoria.getIdCategoria())
               .nombre(categoria.getNombre())
               .build();
    }
}
