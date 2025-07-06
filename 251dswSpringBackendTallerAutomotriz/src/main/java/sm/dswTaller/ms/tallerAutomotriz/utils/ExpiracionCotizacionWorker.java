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
     * Se ejecuta cada 5 minutos (reducido de 1 minuto para optimizar rendimiento)
     */
    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutos
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
     * NOTA: Las cotizaciones pagadas ya no expiran automáticamente, este método se mantiene
     * por compatibilidad pero no se ejecutará automáticamente
     */
    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutos
    @Transactional
    public void verificarCotizacionesPagadasExpiradas() {
        // Las cotizaciones pagadas ya no expiran automáticamente, por lo que este método
        // solo procesará cotizaciones que fueron pagadas antes de este cambio
        List<Cotizacion> cotizacionesPagadasExpiradas = cotizacionRepo.findByEstadoAndFechaExpiracionBefore(
                EstadoCotizacion.PAGADO, LocalDateTime.now());
        
        if (!cotizacionesPagadasExpiradas.isEmpty()) {
            System.out.println("Encontradas " + cotizacionesPagadasExpiradas.size() + " cotizaciones pagadas expiradas (legacy)");
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
     * Verifica cotizaciones próximas a expirar (1 día antes) y envía alertas
     * Se ejecuta cada 30 minutos
     */
    @Scheduled(fixedRate = 1800000) // 1800000 ms = 30 minutos
    @Transactional
    public void verificarCotizacionesProximasAExpirar() {
        LocalDateTime unDiaDespues = LocalDateTime.now().plusDays(1);
        List<Cotizacion> cotizacionesProximas = cotizacionRepo.findByEstadoAndFechaExpiracionBefore(
                EstadoCotizacion.PENDIENTE, unDiaDespues);
        
        for (Cotizacion cotizacion : cotizacionesProximas) {
            if (cotizacion.getFechaExpiracion() != null && cotizacion.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                // Solo mostrar alertas para cotizaciones que aún no han expirado
                long diasRestantes = java.time.Duration.between(LocalDateTime.now(), cotizacion.getFechaExpiracion()).toDays();
                long horasRestantes = java.time.Duration.between(LocalDateTime.now(), cotizacion.getFechaExpiracion()).toHours() % 24;
                
                if (diasRestantes <= 1 && diasRestantes >= 0) {
                    if (diasRestantes == 0 && horasRestantes <= 24) {
                        System.out.println("⚠️ ALERTA: Cotización " + cotizacion.getId() + " expira en " + horasRestantes + " horas");
                    } else if (diasRestantes == 1) {
                        System.out.println("⚠️ ALERTA: Cotización " + cotizacion.getId() + " expira en " + diasRestantes + " día");
                    }
                }
            }
        }
    }
}
