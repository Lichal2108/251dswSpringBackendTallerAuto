/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioByOstDTO {
    private int idOst;
    private Integer kilometraje;
    private String nivelGasolina;
    //private Boolean autorizacionConduccion;
    private List<InventarioAutoRequestDTO> inventario;
}