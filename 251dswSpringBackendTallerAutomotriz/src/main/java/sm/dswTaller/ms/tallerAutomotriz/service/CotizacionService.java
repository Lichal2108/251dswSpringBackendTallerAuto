/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

import jakarta.transaction.Transactional;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialConCantidadResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.ReciboResponseDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialDetalleDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.ServicioDetalleDTO;
import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionMaterial;
import sm.dswTaller.ms.tallerAutomotriz.model.Material;
import sm.dswTaller.ms.tallerAutomotriz.model.Ost;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.MaterialRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.OstRepository;
import sm.dswTaller.ms.tallerAutomotriz.client.VentasClient;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.utils.EstadoCotizacion;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ServicioRepository;
import sm.dswTaller.ms.tallerAutomotriz.service.CotizacionServicioService;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionServicioResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.Servicio;

/**
 *
 * @author Ciro
 */
@Service
public class CotizacionService {
    private static final Logger logger = LoggerFactory.getLogger(CotizacionService.class);
    
    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private CotizacionMaterialService cotizacionMaterialService;
    @Autowired
    private CotizacionServicioService cotizacionServicioService;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private OstRepository ostRepository;
    @Autowired
    private VentasClient ventasClient;
    
    // Listar todas las cotizaciones
    public List<CotizacionResponse> listCotizaciones() {
        return CotizacionResponse.fromEntities(cotizacionRepository.findAll());
    }
    
    // Insertar una nueva cotización
    public CotizacionResponse insertCotizacion(CotizacionRequest request) {
        // Obtener el objeto Ost por ID
        Long idOst = request.getIdOst().longValue();
        Ost ost = ostRepository.findById(idOst).orElse(null);
        if (ost == null) return new CotizacionResponse();

        // Construir la entidad Cotizacion
        Cotizacion cotizacion = Cotizacion.builder()
                .id(request.getId())
                .fecha(request.getFecha())
                .total(request.getTotal())
                .ost(ost)
                .build();

        // Establecer la fecha de expiración (15 minutos desde la creación)
        cotizacion.establecerFechaExpiracion();

        // Guardar en la base de datos
        cotizacion = cotizacionRepository.save(cotizacion);

        // Convertir a DTO de respuesta
        return CotizacionResponse.fromEntity(cotizacion);
    } 

    /**
     * Actualiza el total de una cotización y extiende el tiempo de expiración
     */
    public CotizacionResponse actualizarTotalCotizacion(Long idCotizacion, Double nuevoTotal) {
        Optional<Cotizacion> optional = cotizacionRepository.findById(idCotizacion);

        if (optional.isEmpty()) {
            throw new RuntimeException("No se encontró la cotización con ID: " + idCotizacion);
        }

        Cotizacion cotizacion = optional.get();
        
        // Solo permitir actualización si la cotización está en estado PENDIENTE
        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new RuntimeException("Solo se pueden actualizar cotizaciones en estado PENDIENTE");
        }
        
        cotizacion.setTotal(nuevoTotal);
        
        // Extender el tiempo de expiración por 5 días más desde el momento actual
        cotizacion.establecerFechaExpiracionDesdeAhora();

        cotizacion = cotizacionRepository.save(cotizacion);

        return CotizacionResponse.fromEntity(cotizacion);
    }

    /**
     * Cambia el estado de una cotización a PAGADO, descuenta el stock de los materiales
     * y crea un recibo en el microservicio de ventas
     */
    @Transactional
    public CotizacionResponse pagarCotizacion(Long idCotizacion) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new RuntimeException("Solo se pueden pagar cotizaciones en estado PENDIENTE");
        }

        // Obtener materiales de la cotización
        List<MaterialConCantidadResponse> materiales = cotizacionMaterialService.obtenerMaterialesDeCotizacion(idCotizacion);
        
        // Descontar stock de cada material
        for (MaterialConCantidadResponse materialResponse : materiales) {
            Material material = materialRepository.findById(materialResponse.getIdMaterial())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));
            
            if (!material.reducirStock(materialResponse.getCantidad())) {
                throw new RuntimeException("Stock insuficiente para el material: " + material.getNombre());
            }
            
            materialRepository.save(material);
        }

        // Cambiar estado a PAGADO y establecer fecha de expiración a null para que no expire automáticamente
        cotizacion.setEstado(EstadoCotizacion.PAGADO);
        cotizacion.setFechaExpiracion(null); // Las cotizaciones pagadas no expiran automáticamente
        cotizacion = cotizacionRepository.save(cotizacion);

        // Crear recibo en el microservicio de ventas
        try {
            crearReciboEnVentas(cotizacion);
            logger.info("Recibo creado exitosamente para la cotización {}", idCotizacion);
        } catch (Exception e) {
            logger.error("Error al crear recibo en el microservicio de ventas: {}", e.getMessage());
            // No lanzamos la excepción para no afectar el pago de la cotización
            // En un entorno de producción, podrías implementar un sistema de reintentos
        }

        return CotizacionResponse.fromEntity(cotizacion);
    }

    /**
     * Crea un recibo en el microservicio de ventas
     */
    private void crearReciboEnVentas(Cotizacion cotizacion) {
        try {
            // Obtener información del cliente y técnico desde la OST
            Ost ost = cotizacion.getOst();
            if (ost == null || ost.getAuto() == null || ost.getAuto().getPersona() == null) {
                throw new RuntimeException("No se puede obtener información del cliente");
            }

            // Obtener ID del cliente (propietario del auto)
            Long idCliente = (long) ost.getAuto().getPersona().getIdPersona();
            
            // Obtener ID del técnico (supervisor de la OST) - opcional
            Long idTecnico = null;
            Long idRecepcionista = null;
            
            if (ost.getSupervisor() != null) {
                idTecnico = ost.getSupervisor().getId();
            } else {
                logger.warn("No hay supervisor asignado a la OST {}", ost.getIdOst());
                // Si no hay supervisor, usar el recepcionista como técnico
                if (ost.getRecepcionista() != null) {
                    idTecnico = ost.getRecepcionista().getId();
                }
            }
            
            // Obtener ID del recepcionista - opcional
            if (ost.getRecepcionista() != null) {
                idRecepcionista = ost.getRecepcionista().getId();
            } else {
                logger.warn("No hay recepcionista asignado a la OST {}", ost.getIdOst());
            }

            // Obtener información del vehículo
            String placaAuto = ost.getAuto().getPlaca();
            String marcaAuto = ost.getAuto().getModelo() != null && ost.getAuto().getModelo().getMarca() != null ? 
                ost.getAuto().getModelo().getMarca().getNombre() : "";
            String modeloAuto = ost.getAuto().getModelo() != null ? ost.getAuto().getModelo().getNombre() : "";
            Integer anioAuto = ost.getAuto().getAnio();

            // Obtener información del cliente
            String nombreCliente = ost.getAuto().getPersona().getNombres() + " " + 
                ost.getAuto().getPersona().getApellidoPaterno() + " " + 
                ost.getAuto().getPersona().getApellidoMaterno();
            String documentoCliente = ost.getAuto().getPersona().getNroDocumento();

            // Obtener información del técnico - opcional
            String nombreTecnico = "No asignado";
            if (ost.getSupervisor() != null && ost.getSupervisor().getPersona() != null) {
                nombreTecnico = ost.getSupervisor().getPersona().getNombres() + " " + 
                    ost.getSupervisor().getPersona().getApellidoPaterno();
            } else if (ost.getRecepcionista() != null && ost.getRecepcionista().getPersona() != null) {
                nombreTecnico = ost.getRecepcionista().getPersona().getNombres() + " " + 
                    ost.getRecepcionista().getPersona().getApellidoPaterno();
            }

            // Obtener materiales de la cotización
            List<MaterialConCantidadResponse> materialesResponse = cotizacionMaterialService.obtenerMaterialesDeCotizacion(cotizacion.getId());
            List<MaterialDetalleDTO> materiales = new ArrayList<>();
            BigDecimal subtotalMateriales = BigDecimal.ZERO;

            for (MaterialConCantidadResponse materialResponse : materialesResponse) {
                Material material = materialRepository.findById(materialResponse.getIdMaterial())
                        .orElseThrow(() -> new RuntimeException("Material no encontrado"));
                
                BigDecimal precioUnitario = BigDecimal.valueOf(material.getPrecio());
                BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(materialResponse.getCantidad()));
                
                MaterialDetalleDTO materialDetalle = new MaterialDetalleDTO(
                    material.getId(),
                    material.getNombre(),
                    precioUnitario,
                    materialResponse.getCantidad(),
                    subtotal
                );
                
                materiales.add(materialDetalle);
                subtotalMateriales = subtotalMateriales.add(subtotal);
            }

            // Obtener servicios de la cotización
            List<CotizacionServicioResponse> serviciosResponse = cotizacionServicioService.listarServiciosPorCotizacion(cotizacion.getId());
            List<ServicioDetalleDTO> servicios = new ArrayList<>();
            BigDecimal subtotalServicios = BigDecimal.ZERO;

            for (CotizacionServicioResponse servicioResponse : serviciosResponse) {
                Servicio servicio = servicioRepository.findById(servicioResponse.getIdServicio())
                        .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
                
                BigDecimal precio = BigDecimal.valueOf(servicio.getPrecio());
                
                ServicioDetalleDTO servicioDetalle = new ServicioDetalleDTO(
                    servicio.getId(),
                    servicio.getNombre(),
                    precio
                );
                
                servicios.add(servicioDetalle);
                subtotalServicios = subtotalServicios.add(precio);
            }

            // Crear el DTO para el recibo
            ReciboRequestDTO reciboRequest = new ReciboRequestDTO(
                idCliente,
                idTecnico,
                idRecepcionista,
                BigDecimal.valueOf(cotizacion.getTotal()),
                LocalDate.now(),
                cotizacion.getId(),
                ost.getIdOst(),
                ost.getAuto().getIdAuto(),
                placaAuto,
                marcaAuto,
                modeloAuto,
                anioAuto,
                nombreCliente,
                documentoCliente,
                nombreTecnico,
                materiales,
                servicios,
                subtotalMateriales,
                subtotalServicios,
                "Recibo generado automáticamente al pagar cotización"
            );

            // Enviar al microservicio de ventas
            var response = ventasClient.crearRecibo(reciboRequest);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                ReciboResponseDTO reciboResponse = response.getBody();
                logger.info("Recibo creado con ID: {}", reciboResponse != null ? reciboResponse.getIdRecibo() : "N/A");
            } else {
                throw new RuntimeException("Error al crear recibo en el microservicio de ventas");
            }
            
        } catch (Exception e) {
            logger.error("Error en la comunicación con el microservicio de ventas: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Expira una cotización y libera el stock de los materiales si estaba reservado
     */
    @Transactional
    public CotizacionResponse expirarCotizacion(Long idCotizacion) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new RuntimeException("Solo se pueden expirar cotizaciones en estado PENDIENTE");
        }

        // Cambiar estado a EXPIRADO
        cotizacion.setEstado(EstadoCotizacion.EXPIRADO);
        cotizacion = cotizacionRepository.save(cotizacion);

        return CotizacionResponse.fromEntity(cotizacion);
    }

    /**
     * Libera el stock de materiales de una cotización expirada o cancelada
     */
    @Transactional
    public void liberarStockCotizacion(Long idCotizacion) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // Solo liberar stock si la cotización está en estado PAGADO y se va a cancelar/expirar
        if (cotizacion.getEstado() == EstadoCotizacion.PAGADO) {
            List<MaterialConCantidadResponse> materiales = cotizacionMaterialService.obtenerMaterialesDeCotizacion(idCotizacion);
            
            for (MaterialConCantidadResponse materialResponse : materiales) {
                Material material = materialRepository.findById(materialResponse.getIdMaterial())
                        .orElseThrow(() -> new RuntimeException("Material no encontrado"));
                
                // Restaurar el stock
                material.restaurarStock(materialResponse.getCantidad());
                materialRepository.save(material);
            }
        }
    }

    /**
     * Cancela una cotización pagada y libera el stock
     */
    @Transactional
    public CotizacionResponse cancelarCotizacion(Long idCotizacion) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        if (cotizacion.getEstado() != EstadoCotizacion.PAGADO) {
            throw new RuntimeException("Solo se pueden cancelar cotizaciones en estado PAGADO");
        }

        // Liberar el stock primero
        liberarStockCotizacion(idCotizacion);

        // Cambiar estado a EXPIRADO (o crear un nuevo estado CANCELADO si es necesario)
        cotizacion.setEstado(EstadoCotizacion.EXPIRADO);
        cotizacion = cotizacionRepository.save(cotizacion);

        return CotizacionResponse.fromEntity(cotizacion);
    }

    /**
     * Extiende el tiempo de expiración de una cotización pendiente
     */
    public CotizacionResponse extenderTiempoExpiracion(Long idCotizacion) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        if (cotizacion.getEstado() != EstadoCotizacion.PENDIENTE) {
            throw new RuntimeException("Solo se puede extender el tiempo de cotizaciones en estado PENDIENTE");
        }

        // Extender el tiempo de expiración por 5 días más
        cotizacion.establecerFechaExpiracionDesdeAhora();
        cotizacion = cotizacionRepository.save(cotizacion);

        return CotizacionResponse.fromEntity(cotizacion);
    }
}
