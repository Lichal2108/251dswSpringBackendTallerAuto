/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Material;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialResponse {
    private Long id;
    private String nombre;
    private Integer stock;
    private Double precio;

    public static MaterialResponse fromEntity(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .nombre(material.getNombre())
                .stock(material.getStock())
                .precio(material.getPrecio())
                .build();
    }

    public static List<MaterialResponse> fromEntities(List<Material> materiales) {
        return materiales.stream()
                .map(MaterialResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
}
