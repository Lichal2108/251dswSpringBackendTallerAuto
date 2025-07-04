/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Servicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private Long id;
    private String nombre;
    private Double precio;

    public static ServicioResponse fromEntity(Servicio servicio) {
        return ServicioResponse.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .precio(servicio.getPrecio())
                .build();
    }

    public static List<ServicioResponse> fromEntities(List<Servicio> servicios) {
        return servicios.stream()
                .map(ServicioResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
}
