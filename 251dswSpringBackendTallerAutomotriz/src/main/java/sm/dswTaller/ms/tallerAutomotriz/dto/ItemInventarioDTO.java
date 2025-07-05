/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.ItemInventario;

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
