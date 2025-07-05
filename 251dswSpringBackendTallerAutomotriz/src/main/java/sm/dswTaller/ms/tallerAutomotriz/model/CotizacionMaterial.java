/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionMaterial {
    @EmbeddedId
    private CotizacionMaterialId id;

    @ManyToOne
    @MapsId("cotizacionId")
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

    private int cantidad;

    @Setter
    @Getter
    @Transient
    @Builder.Default
    private boolean stockDescontado = false;


}
