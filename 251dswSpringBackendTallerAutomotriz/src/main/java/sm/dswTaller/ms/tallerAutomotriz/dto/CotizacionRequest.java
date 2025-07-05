/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ciro
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionRequest {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private Integer idOst;
    
}
