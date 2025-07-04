package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.InventarioAuto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioAutoResponseDTO {
    private Long idInventario;
    private int idOst;
    private Long idItem;
    private String nombreItem;
    private String categoriaItem;
    private Integer cantidad;
    private String estado;
    
    public static InventarioAutoResponseDTO fromEntity(InventarioAuto entity) {
        return InventarioAutoResponseDTO.builder()
                .idInventario(entity.getIdInventario())
                .idOst(entity.getOst().getIdOst())
                .idItem(entity.getItem().getIdItem())
                .nombreItem(entity.getItem().getNombre())
                .categoriaItem(entity.getItem().getCategoria().getNombre())
                .cantidad(entity.getCantidad())
                .estado(entity.getEstado())
                .build();
    }

    public static List<InventarioAutoResponseDTO> fromEntities(List<InventarioAuto> entities) {
        return entities.stream()
                .map(InventarioAutoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}