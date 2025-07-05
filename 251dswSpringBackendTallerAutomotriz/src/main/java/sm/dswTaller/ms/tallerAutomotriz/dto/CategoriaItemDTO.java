/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.CategoriaItem;

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
