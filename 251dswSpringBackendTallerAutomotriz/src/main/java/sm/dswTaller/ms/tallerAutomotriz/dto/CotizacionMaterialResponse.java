/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.Material;

/**
 *
 * @author Ciro
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionMaterialResponse {
    private Long id;
    private String nombre;
    private Integer stock;
    private Double precio;
    private boolean stockDescontado;

    public static CotizacionMaterialResponse fromEntity(Material material) {
        return CotizacionMaterialResponse.builder()
                .id(material.getId())
                .nombre(material.getNombre())
                .stock(material.getStock())
                .precio(material.getPrecio())
                .build();
    }    
}
