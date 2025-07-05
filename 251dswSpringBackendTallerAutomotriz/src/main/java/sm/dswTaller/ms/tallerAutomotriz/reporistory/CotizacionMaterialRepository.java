/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.reporistory;

/**
 *
 * @author Ciro
 */
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionMaterial;
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionMaterialId;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionMaterialRepository extends JpaRepository<CotizacionMaterial, CotizacionMaterialId> {
    List<CotizacionMaterial> findByCotizacionId(Long cotizacionId);

    
}
