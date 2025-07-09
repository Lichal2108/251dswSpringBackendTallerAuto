/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.Modelo;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;

/**
 *
 * @author Aldair
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoDTO {
    private Integer idAuto;
    private String placa;
    private String modelo;
    private Integer anio;
    private String color;
    private Persona persona;
}
