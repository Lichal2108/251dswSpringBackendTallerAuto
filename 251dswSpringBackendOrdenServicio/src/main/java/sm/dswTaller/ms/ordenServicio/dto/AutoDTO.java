/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldair
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoDTO {
    private String placa;
    private String modelo;
    private Integer anio;
    private String color;
    private Long idPersona;
}
