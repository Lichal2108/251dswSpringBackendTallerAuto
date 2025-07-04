package sm.dswTaller.ms.tallerAutomotriz.reporistory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Ciro
 */
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionServicio;
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionServicioId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionServicioRepository extends JpaRepository<CotizacionServicio, CotizacionServicioId> {
    List<CotizacionServicio> findByCotizacionId(Long cotizacionId);

    
    
}
