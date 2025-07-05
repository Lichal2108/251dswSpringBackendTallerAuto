/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ciro
 */
@Embeddable
@Data  
@NoArgsConstructor
@AllArgsConstructor
 public class CotizacionMaterialId implements Serializable{
    @Column(name = "cotizacion_id")       // Mapea al nombre exacto de la columna
    private Long cotizacionId;
    @Column(name = "material_id")       // Mapea al nombre exacto de la columna
    private Long materialId;    
}
