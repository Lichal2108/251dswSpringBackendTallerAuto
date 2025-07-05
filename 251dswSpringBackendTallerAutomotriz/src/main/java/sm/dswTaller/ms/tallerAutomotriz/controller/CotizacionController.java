/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import java.util.Map;
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
        CotizacionResponse response;
        try {
            response = cotizacionService.insertCotizacion(request);
        } catch (Exception e) {
            logger.error("Error inesperado al insertar cotizacion", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .message("Error al procesar materiales: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/actualizar-total")
    public ResponseEntity<?> actualizarTotalCotizacion(@RequestBody ActualizarTotalCotizacionRequest request) {
        logger.info("Actualizando total para cotización ID: {}", request.getIdCotizacion());

        try {
            cotizacionService.actualizarTotalCotizacion(
                    request.getIdCotizacion(), request.getNuevoTotal()
            );

            return ResponseEntity.ok(Map.of("mensaje", "Total actualizado correctamente"));
        } catch (Exception e) {
            logger.error("Error al actualizar el total de la cotización", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar el total: " + e.getMessage()));
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
}
