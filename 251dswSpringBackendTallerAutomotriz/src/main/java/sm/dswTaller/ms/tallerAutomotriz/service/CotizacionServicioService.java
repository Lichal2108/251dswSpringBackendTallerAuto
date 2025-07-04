/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

/**
 *
 * @author Ciro
 */
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMultiplesServiciosRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarServicioRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMultiplesServiciosResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionServicioResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.*;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionServicioRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ServicioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CotizacionServicioService {

    @Autowired
    private CotizacionRepository cotizacionRepo;

    @Autowired
    private ServicioRepository servicioRepo;

    @Autowired
    private CotizacionServicioRepository cotizacionServicioRepo;

    public CotizacionServicioResponse agregarServicioACotizacion(AgregarServicioRequest request) {
        Cotizacion cotizacion = cotizacionRepo.findById(request.getIdCotizacion())
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        Servicio servicio = servicioRepo.findById(request.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        CotizacionServicioId id = new CotizacionServicioId(request.getIdCotizacion(), request.getIdServicio());

        if (cotizacionServicioRepo.existsById(id)) {
            throw new RuntimeException("El servicio ya está agregado a esta cotización.");
        }

        CotizacionServicio cotizacionServicio = CotizacionServicio.builder()
                .id(id)
                .cotizacion(cotizacion)
                .servicio(servicio)
                .build();

        cotizacionServicioRepo.save(cotizacionServicio);

        return CotizacionServicioResponse.fromEntity(servicio);
    }

    // Método para agregar múltiples servicios
    @Transactional
    public CotizacionMultiplesServiciosResponse agregarMultiplesServiciosACotizacion(AgregarMultiplesServiciosRequest request) {
        Cotizacion cotizacion = cotizacionRepo.findById(request.getIdCotizacion())
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        List<CotizacionServicioResponse> serviciosAgregados = request.getIdServicios().stream()
                .map(idServicio -> {
                    try {
                        Servicio servicio = servicioRepo.findById(idServicio)
                                .orElseThrow(() -> new RuntimeException("Servicio con ID " + idServicio + " no encontrado"));

                        CotizacionServicioId id = new CotizacionServicioId(request.getIdCotizacion(), idServicio);

                        if (!cotizacionServicioRepo.existsById(id)) {
                            CotizacionServicio cotizacionServicio = CotizacionServicio.builder()
                                    .id(id)
                                    .cotizacion(cotizacion)
                                    .servicio(servicio)
                                    .build();

                            cotizacionServicioRepo.save(cotizacionServicio);
                            return CotizacionServicioResponse.fromEntity(servicio);
                        }
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return CotizacionMultiplesServiciosResponse.builder()
                .idCotizacion(request.getIdCotizacion())
                .serviciosAgregados(serviciosAgregados)
                .build();
    }
public List<CotizacionServicioResponse> listarServiciosPorCotizacion(Long idCotizacion) {
    List<CotizacionServicio> asociaciones = cotizacionServicioRepo.findByCotizacionId(idCotizacion);

    return asociaciones.stream()
            .map(cs -> CotizacionServicioResponse.fromEntity(cs.getServicio()))
            .collect(Collectors.toList());
}    
}
