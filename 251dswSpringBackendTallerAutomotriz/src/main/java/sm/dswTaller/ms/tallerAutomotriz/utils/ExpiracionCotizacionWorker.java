package sm.dswTaller.ms.tallerAutomotriz.utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialConCantidadResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import sm.dswTaller.ms.tallerAutomotriz.model.Material;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.MaterialRepository;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionMaterialService;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExpiracionCotizacionWorker {

    @Autowired
    private CotizacionRepository cotizacionRepo;

    @Autowired
    private CotizacionMaterialService cotizacionMaterialService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CotizacionService cotizacionService;

    /**
     * Verifica y expira cotizaciones pendientes que han superado su tiempo de expiración
     * Se ejecuta cada minuto
     */
    @Scheduled(fixedRate = 60000) // 60000 ms = 1 minuto
    @Transactional
    public void verificarCotizacionesExpiradas() {
        List<Cotizacion> cotizacionesExpiradas = cotizacionRepo.findByEstadoAndFechaExpiracionBefore(
                EstadoCotizacion.PENDIENTE, LocalDateTime.now());
        
        if (!cotizacionesExpiradas.isEmpty()) {
            System.out.println("Encontradas " + cotizacionesExpiradas.size() + " cotizaciones pendientes expiradas");
        }
        
        for (Cotizacion cotizacion : cotizacionesExpiradas) {
            try {
                // Usar el servicio para expirar la cotización
                cotizacionService.expirarCotizacion(cotizacion.getId());
                System.out.println("✅ Cotización " + cotizacion.getId() + " expirada automáticamente");
            } catch (Exception e) {
                System.err.println("❌ Error al expirar cotización " + cotizacion.getId() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Verifica cotizaciones pagadas que han expirado y libera el stock
     * Se ejecuta cada 5 minutos
     */
    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutos
    @Transactional
    public void verificarCotizacionesPagadasExpiradas() {
        List<Cotizacion> cotizacionesPagadasExpiradas = cotizacionRepo.findByEstadoAndFechaExpiracionBefore(
                EstadoCotizacion.PAGADO, LocalDateTime.now());
        
        if (!cotizacionesPagadasExpiradas.isEmpty()) {
            System.out.println("Encontradas " + cotizacionesPagadasExpiradas.size() + " cotizaciones pagadas expiradas");
        }
        
        for (Cotizacion cotizacion : cotizacionesPagadasExpiradas) {
            try {
                // Liberar el stock de materiales
                cotizacionService.liberarStockCotizacion(cotizacion.getId());
                
                // Cambiar estado a EXPIRADO
                cotizacion.setEstado(EstadoCotizacion.EXPIRADO);
                cotizacionRepo.save(cotizacion);
                
                System.out.println("✅ Stock liberado para cotización pagada expirada " + cotizacion.getId());
            } catch (Exception e) {
                System.err.println("❌ Error al liberar stock de cotización " + cotizacion.getId() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Verifica cotizaciones próximas a expirar (5 minutos antes) y envía alertas
     * Se ejecuta cada 2 minutos
     */
    @Scheduled(fixedRate = 120000) // 120000 ms = 2 minutos
    @Transactional
    public void verificarCotizacionesProximasAExpirar() {
        LocalDateTime cincoMinutosDespues = LocalDateTime.now().plusMinutes(5);
        List<Cotizacion> cotizacionesProximas = cotizacionRepo.findByEstadoAndFechaExpiracionBefore(
                EstadoCotizacion.PENDIENTE, cincoMinutosDespues);
        
        for (Cotizacion cotizacion : cotizacionesProximas) {
            if (cotizacion.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                // Solo mostrar alertas para cotizaciones que aún no han expirado
                long minutosRestantes = java.time.Duration.between(LocalDateTime.now(), cotizacion.getFechaExpiracion()).toMinutes();
                if (minutosRestantes <= 5 && minutosRestantes > 0) {
                    System.out.println("⚠️ ALERTA: Cotización " + cotizacion.getId() + " expira en " + minutosRestantes + " minutos");
                }
            }
        }
    }
}
