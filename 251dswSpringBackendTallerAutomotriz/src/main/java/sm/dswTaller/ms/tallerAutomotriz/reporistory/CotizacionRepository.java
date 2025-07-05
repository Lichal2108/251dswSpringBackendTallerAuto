/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.tallerAutomotriz.utils.EstadoCotizacion;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Ciro
 */
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion,Long>{
    List<Cotizacion> findByEstadoAndFechaExpiracionBefore(
            EstadoCotizacion estado,
            LocalDateTime fecha);

    List<Cotizacion> findByEstado(EstadoCotizacion estado);

}
