/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import static io.swagger.v3.core.util.Json.mapper;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionServicioService;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionMaterialService;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionService;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionResponse;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionServicioResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarServicioRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMaterialResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMaterialRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMultiplesServiciosRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMultiplesServiciosResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMultiplesMaterialesRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMultiplesMaterialesResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.ActualizarTotalCotizacionRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialConCantidadResponse;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import sm.dswTaller.ms.tallerAutomotriz.utils.EstadoCotizacion;

@RestController
@RequestMapping("/api/v1/cotizaciones")
public class CotizacionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CotizacionServicioService cotizacionServicioService;

    @Autowired
    private CotizacionMaterialService cotizacionMaterialService;

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @GetMapping
    public ResponseEntity<?> getCotizaciones() {
        List<CotizacionResponse> listaCotizaciones = null;
        try {
            listaCotizaciones = cotizacionService.listCotizaciones();
        } catch (Exception e) {
            logger.error("Error inesperado al listar cotizaciones", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (listaCotizaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("cotizaciones not found").build());
        }
        return ResponseEntity.ok(listaCotizaciones);
    }

    @PostMapping
    public ResponseEntity<?> insertCotizacion(@RequestBody CotizacionRequest request) {
        logger.info("> insertCotizacion: " + request.toString());
        
        // Validaciones básicas
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message("Request no puede ser nulo").build());
        }
        
        if (request.getIdOst() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message("idOst es requerido").build());
        }
        
        if (request.getFecha() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message("fecha es requerida").build());
        }
        
        CotizacionResponse response;
        try {
            response = cotizacionService.insertCotizacion(request);
        } catch (Exception e) {
            logger.error("Error inesperado al insertar cotizacion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder().message("Error interno: " + e.getMessage()).build());
        }

        if (response == null || response.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("cotizacion not inserted").build());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/agregar-servicio")
    public CotizacionServicioResponse agregarServicio(@RequestBody AgregarServicioRequest request) {
        return cotizacionServicioService.agregarServicioACotizacion(request);
    }

    @PostMapping("/agregar-material")
    public CotizacionMaterialResponse agregarMaterial(@RequestBody AgregarMaterialRequest request) {
        return cotizacionMaterialService.agregarMaterialACotizacion(request);
    }

    @PostMapping("/agregar-servicios")
    public ResponseEntity<?> agregarMultiplesServicios(@RequestBody AgregarMultiplesServiciosRequest request) {
        logger.info("> agregarMultiplesServicios: " + request.toString());

        if (request.getIdServicios() == null || request.getIdServicios().isEmpty()) {
            return ResponseEntity.ok(CotizacionMultiplesServiciosResponse.builder()
                    .idCotizacion(request.getIdCotizacion())
                    .serviciosAgregados(List.of())
                    .build());
        }

        try {
            CotizacionMultiplesServiciosResponse response = cotizacionServicioService.agregarMultiplesServiciosACotizacion(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al agregar múltiples servicios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al agregar servicios: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/agregar-materiales")
    public ResponseEntity<?> agregarMultiplesMateriales(
            @RequestBody AgregarMultiplesMaterialesRequest request) {

        logger.info("Solicitud para agregar materiales a cotización: {}", request.getIdCotizacion());

        if (request.getMateriales() == null || request.getMateriales().isEmpty()) {
            return ResponseEntity.ok(CotizacionMultiplesMaterialesResponse.builder()
                    .idCotizacion(request.getIdCotizacion())
                    .materialesAgregados(List.of())
                    .build());
        }

        try {
            CotizacionMultiplesMaterialesResponse response =
                    cotizacionMaterialService.agregarMultiplesMaterialesACotizacion(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al agregar materiales: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al procesar materiales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PostMapping("/actualizar-total")
    public ResponseEntity<?> actualizarTotalCotizacion(@RequestBody ActualizarTotalCotizacionRequest request) {
        logger.info("Actualizando total para cotización ID: {}", request.getIdCotizacion());

        try {
            cotizacionService.actualizarTotalCotizacion(
                    request.getIdCotizacion(), request.getNuevoTotal()
            );

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("mensaje", "Total actualizado correctamente");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            logger.error("Error al actualizar el total de la cotización", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al actualizar el total: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
    @GetMapping("/{idCotizacion}/servicios")
    public ResponseEntity<?> listarServiciosPorCotizacion(@PathVariable Long idCotizacion) {
        try {
            List<CotizacionServicioResponse> servicios = cotizacionServicioService.listarServiciosPorCotizacion(idCotizacion);

            if (servicios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.builder().message("No se encontraron servicios para la cotización").build());
            }

            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            logger.error("Error al listar servicios de la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder().message("Error interno: " + e.getMessage()).build());
        }
        
    }
    @GetMapping("/{id}/materiales")
    public ResponseEntity<List<MaterialConCantidadResponse>> obtenerMaterialesPorCotizacion(
            @PathVariable("id") Long idCotizacion) {
        List<MaterialConCantidadResponse> materiales = cotizacionMaterialService.obtenerMaterialesDeCotizacion(idCotizacion);
        return ResponseEntity.ok(materiales);
    }   

    @PostMapping("/{idCotizacion}/pagar")
    public ResponseEntity<?> pagarCotizacion(@PathVariable Long idCotizacion) {
        logger.info("Pagando cotización ID: {}", idCotizacion);
        
        try {
            CotizacionResponse response = cotizacionService.pagarCotizacion(idCotizacion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al pagar la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al pagar la cotización: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/{idCotizacion}/expirar")
    public ResponseEntity<?> expirarCotizacion(@PathVariable Long idCotizacion) {
        logger.info("Expirando cotización ID: {}", idCotizacion);
        
        try {
            CotizacionResponse response = cotizacionService.expirarCotizacion(idCotizacion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al expirar la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al expirar la cotización: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/{idCotizacion}/cancelar")
    public ResponseEntity<?> cancelarCotizacion(@PathVariable Long idCotizacion) {
        logger.info("Cancelando cotización ID: {}", idCotizacion);
        
        try {
            CotizacionResponse response = cotizacionService.cancelarCotizacion(idCotizacion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al cancelar la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al cancelar la cotización: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/{idCotizacion}/extender-tiempo")
    public ResponseEntity<?> extenderTiempoExpiracion(@PathVariable Long idCotizacion) {
        logger.info("Extendiendo tiempo de expiración para cotización ID: {}", idCotizacion);
        
        try {
            CotizacionResponse response = cotizacionService.extenderTiempoExpiracion(idCotizacion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al extender el tiempo de expiración", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al extender el tiempo de expiración: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/{idCotizacion}/estado")
    public ResponseEntity<?> obtenerEstadoCotizacion(@PathVariable Long idCotizacion) {
        logger.info("Obteniendo estado de cotización ID: {}", idCotizacion);
        
        try {
            // Buscar la cotización directamente en el repositorio para obtener información completa
            var cotizacionOpt = cotizacionRepository.findById(idCotizacion);
            
            if (cotizacionOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.builder()
                                .message("Cotización no encontrada")
                                .build());
            }
            
            var cotizacionCompleta = cotizacionOpt.get();
            CotizacionResponse cotizacion = CotizacionResponse.fromEntity(cotizacionCompleta);
            List<MaterialConCantidadResponse> materiales = cotizacionMaterialService.obtenerMaterialesDeCotizacion(idCotizacion);
            
            // Calcular tiempo restante hasta expiración
            long minutosRestantes = 0;
            boolean expirada = false;
            boolean proximaAExpirar = false;
            
            LocalDateTime fechaExpiracion = cotizacionCompleta.getFechaExpiracion();
            
            // Si la cotización está pagada, no tiene fecha de expiración automática
            if (cotizacionCompleta.getEstado() == EstadoCotizacion.PAGADO) {
                expirada = false;
                proximaAExpirar = false;
                minutosRestantes = -1; // Indica que no expira automáticamente
            } else if (fechaExpiracion != null) {
                if (LocalDateTime.now().isAfter(fechaExpiracion)) {
                    expirada = true;
                    minutosRestantes = 0;
                } else {
                    minutosRestantes = java.time.Duration.between(LocalDateTime.now(), fechaExpiracion).toMinutes();
                    // Para cotizaciones de 5 días, mostrar alerta cuando falte 1 día o menos
                    proximaAExpirar = minutosRestantes <= 1440 && minutosRestantes > 0; // 1440 minutos = 24 horas
                }
            }
            
            Map<String, Object> response = Map.of(
                "cotizacion", cotizacion,
                "materiales", materiales,
                "totalMateriales", materiales.size(),
                "materialesConStockDescontado", materiales.stream().filter(m -> m.isStockDescontado()).count(),
                "tiempoExpiracion", Map.of(
                    "minutosRestantes", minutosRestantes,
                    "expirada", expirada,
                    "proximaAExpirar", proximaAExpirar,
                    "fechaExpiracion", fechaExpiracion
                )
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener estado de la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al obtener estado de la cotización: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/por-ost/{idOst}")
    public ResponseEntity<?> obtenerCotizacionPorOst(@PathVariable Integer idOst) {
        Optional<Cotizacion> cotizacion = null;
        try {
            cotizacion = cotizacionRepository.findByOstIdOst(idOst);
        } catch (Exception e) {
            logger.error("Error inesperado al buscar ost", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cotizacion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("cotizaciones not found").build());
        }
        return ResponseEntity.ok(CotizacionResponse.fromEntity(cotizacion.get()));
    }
}
