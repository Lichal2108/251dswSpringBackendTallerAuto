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
public class MaterialConCantidadResponse {
    private Long idMaterial;
    private String nombre;
    private Integer stock;
    private Double precio;
    private Integer cantidad;

    public static MaterialConCantidadResponse fromEntity(Material material, int cantidad) {
        return MaterialConCantidadResponse.builder()
                .idMaterial(material.getId())
                .nombre(material.getNombre())
                .stock(material.getStock())
                .precio(material.getPrecio())
                .cantidad(cantidad)
                .build();
    }    
    
}
