package sm.dswTaller.ms.tallerVentas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.dswTaller.ms.tallerVentas.dto.*;
import sm.dswTaller.ms.tallerVentas.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboResponseDTO;
import sm.dswTaller.ms.tallerVentas.dto.MaterialDetalleDTO;
import sm.dswTaller.ms.tallerVentas.dto.ServicioDetalleDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboClienteDTO;
import sm.dswTaller.ms.tallerVentas.dto.DetalleReciboDTO;
import sm.dswTaller.ms.tallerVentas.model.Recibo;
import sm.dswTaller.ms.tallerVentas.repository.ReciboRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReciboService {
    
    @Autowired
    private ReciboRepository reciboRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PersistenceContext
    private EntityManager entityManager;
    
    public List<ReciboResponseDTO> getAllRecibos() {
        return reciboRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<ReciboResponseDTO> getReciboById(Long id) {
        return reciboRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public ReciboResponseDTO createRecibo(ReciboRequestDTO request) {
        Recibo recibo = new Recibo();
        
        // Campos básicos
        recibo.setIdCliente(request.getIdCliente());
        recibo.setIdTecnico(request.getIdTecnico());
        recibo.setIdRecepcionista(request.getIdRecepcionista());
        recibo.setMontoTotal(request.getMontoTotal());
        recibo.setFecha(request.getFecha());
        
        // Información de la cotización
        recibo.setIdCotizacion(request.getIdCotizacion());
        recibo.setIdOst(request.getIdOst());
        
        // Información del vehículo
        recibo.setIdAuto(request.getIdAuto());
        recibo.setPlacaAuto(request.getPlacaAuto());
        recibo.setMarcaAuto(request.getMarcaAuto());
        recibo.setModeloAuto(request.getModeloAuto());
        recibo.setAnioAuto(request.getAnioAuto());
        
        // Información del cliente
        recibo.setNombreCliente(request.getNombreCliente());
        recibo.setDocumentoCliente(request.getDocumentoCliente());
        
        // Información del técnico
        recibo.setNombreTecnico(request.getNombreTecnico());
        
        // Convertir materiales y servicios a JSON
        try {
            if (request.getMateriales() != null) {
                recibo.setMaterialesDetalle(objectMapper.writeValueAsString(request.getMateriales()));
            }
            if (request.getServicios() != null) {
                recibo.setServiciosDetalle(objectMapper.writeValueAsString(request.getServicios()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir materiales/servicios a JSON", e);
        }
        
        // Subtotales
        recibo.setSubtotalMateriales(request.getSubtotalMateriales() != null ? request.getSubtotalMateriales() : java.math.BigDecimal.ZERO);
        recibo.setSubtotalServicios(request.getSubtotalServicios() != null ? request.getSubtotalServicios() : java.math.BigDecimal.ZERO);
        
        // Información adicional
        recibo.setObservaciones(request.getObservaciones());
        
        Recibo savedRecibo = reciboRepository.save(recibo);
        return convertToResponse(savedRecibo);
    }
    
    public Optional<ReciboResponseDTO> updateRecibo(Long id, ReciboRequestDTO request) {
        return reciboRepository.findById(id)
                .map(recibo -> {
                    // Actualizar campos básicos
                    recibo.setIdCliente(request.getIdCliente());
                    recibo.setIdTecnico(request.getIdTecnico());
                    recibo.setIdRecepcionista(request.getIdRecepcionista());
                    recibo.setMontoTotal(request.getMontoTotal());
                    recibo.setFecha(request.getFecha());
                    
                    // Actualizar información de la cotización
                    recibo.setIdCotizacion(request.getIdCotizacion());
                    recibo.setIdOst(request.getIdOst());
                    
                    // Actualizar información del vehículo
                    recibo.setIdAuto(request.getIdAuto());
                    recibo.setPlacaAuto(request.getPlacaAuto());
                    recibo.setMarcaAuto(request.getMarcaAuto());
                    recibo.setModeloAuto(request.getModeloAuto());
                    recibo.setAnioAuto(request.getAnioAuto());
                    
                    // Actualizar información del cliente
                    recibo.setNombreCliente(request.getNombreCliente());
                    recibo.setDocumentoCliente(request.getDocumentoCliente());
                    
                    // Actualizar información del técnico
                    recibo.setNombreTecnico(request.getNombreTecnico());
                    
                    // Convertir materiales y servicios a JSON
                    try {
                        if (request.getMateriales() != null) {
                            recibo.setMaterialesDetalle(objectMapper.writeValueAsString(request.getMateriales()));
                        }
                        if (request.getServicios() != null) {
                            recibo.setServiciosDetalle(objectMapper.writeValueAsString(request.getServicios()));
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error al convertir materiales/servicios a JSON", e);
                    }
                    
                    // Actualizar subtotales
                    recibo.setSubtotalMateriales(request.getSubtotalMateriales() != null ? request.getSubtotalMateriales() : java.math.BigDecimal.ZERO);
                    recibo.setSubtotalServicios(request.getSubtotalServicios() != null ? request.getSubtotalServicios() : java.math.BigDecimal.ZERO);
                    
                    // Actualizar información adicional
                    recibo.setObservaciones(request.getObservaciones());
                    
                    Recibo savedRecibo = reciboRepository.save(recibo);
                    return convertToResponse(savedRecibo);
                });
    }
    
    public boolean deleteRecibo(Long id) {
        if (reciboRepository.existsById(id)) {
            reciboRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean marcarReciboParaEvaluacion(Long id) {
        return reciboRepository.findById(id)
                .map(recibo -> {
                    recibo.setEstadoRecibo("LISTO_PARA_EVALUACION");
                    reciboRepository.save(recibo);
                    return true;
                })
                .orElse(false);
    }
    
    public boolean verificarReciboListoParaEvaluacion(Long id) {
        return reciboRepository.findById(id)
                .map(recibo -> "LISTO_PARA_EVALUACION".equals(recibo.getEstadoRecibo()))
                .orElse(false);
    }
    
    private ReciboResponseDTO convertToResponse(Recibo recibo) {
        List<MaterialDetalleDTO> materiales = null;
        List<ServicioDetalleDTO> servicios = null;
        
        // Convertir JSON de vuelta a objetos
        try {
            if (recibo.getMaterialesDetalle() != null) {
                materiales = objectMapper.readValue(recibo.getMaterialesDetalle(), new TypeReference<List<MaterialDetalleDTO>>() {});
            }
            if (recibo.getServiciosDetalle() != null) {
                servicios = objectMapper.readValue(recibo.getServiciosDetalle(), new TypeReference<List<ServicioDetalleDTO>>() {});
            }
        } catch (JsonProcessingException e) {
            // Log error pero no fallar la conversión
            System.err.println("Error al convertir JSON de materiales/servicios: " + e.getMessage());
        }
        
        return new ReciboResponseDTO(
                recibo.getIdRecibo(),
                recibo.getIdCliente(),
                recibo.getIdTecnico(),
                recibo.getIdRecepcionista(),
                recibo.getMontoTotal(),
                recibo.getFecha(),
                recibo.getIdCotizacion(),
                recibo.getIdOst(),
                recibo.getIdAuto(),
                recibo.getPlacaAuto(),
                recibo.getMarcaAuto(),
                recibo.getModeloAuto(),
                recibo.getAnioAuto(),
                recibo.getNombreCliente(),
                recibo.getDocumentoCliente(),
                recibo.getNombreTecnico(),
                materiales,
                servicios,
                recibo.getSubtotalMateriales(),
                recibo.getSubtotalServicios(),
                recibo.getObservaciones(),
                recibo.getEstadoRecibo(),
                recibo.getFechaCreacion()
        );
    }
    
    // Métodos para el cliente
    public List<ReciboClienteDTO> obtenerRecibosPorCliente(Long idCliente) {
        return reciboRepository.findByIdCliente(idCliente).stream()
                .map(this::convertToClienteDTO)
                .collect(Collectors.toList());
    }
    
    public DetalleReciboDTO obtenerDetalleRecibo(Long idRecibo) {
        return reciboRepository.findById(idRecibo)
                .map(this::convertToDetalleDTO)
                .orElse(null);
    }
    
    public byte[] generarPDFRecibo(Long idRecibo) {
        // Por ahora retornamos null, se puede implementar la generación de PDF más adelante
        return null;
    }
    
    private ReciboClienteDTO convertToClienteDTO(Recibo recibo) {
        ReciboClienteDTO dto = new ReciboClienteDTO();
        dto.setIdRecibo(recibo.getIdRecibo());
        dto.setIdCotizacion(recibo.getIdCotizacion());
        dto.setFecha(recibo.getFecha().atStartOfDay()); // Convertir LocalDate a LocalDateTime
        dto.setMontoTotal(recibo.getMontoTotal().doubleValue());
        dto.setNombreCliente(recibo.getNombreCliente());
        dto.setPlacaAuto(recibo.getPlacaAuto());
        dto.setMarcaAuto(recibo.getMarcaAuto());
        dto.setModeloAuto(recibo.getModeloAuto());
        dto.setAnioAuto(recibo.getAnioAuto());
        dto.setObservaciones(recibo.getObservaciones());
        dto.setEstadoRecibo(recibo.getEstadoRecibo());
        dto.setFechaCreacion(recibo.getFechaCreacion());
        dto.setSubtotalMateriales(recibo.getSubtotalMateriales().doubleValue());
        dto.setSubtotalServicios(recibo.getSubtotalServicios().doubleValue());
        return dto;
    }
    
    private DetalleReciboDTO convertToDetalleDTO(Recibo recibo) {
        List<DetalleReciboDTO.MaterialReciboDTO> materiales = null;
        List<DetalleReciboDTO.ServicioReciboDTO> servicios = null;
        
        // Convertir JSON de vuelta a objetos
        try {
            if (recibo.getMaterialesDetalle() != null) {
                List<MaterialDetalleDTO> materialesDetalle = objectMapper.readValue(
                    recibo.getMaterialesDetalle(), 
                    new TypeReference<List<MaterialDetalleDTO>>() {}
                );
                materiales = materialesDetalle.stream()
                    .map(m -> {
                        DetalleReciboDTO.MaterialReciboDTO materialDTO = new DetalleReciboDTO.MaterialReciboDTO();
                        materialDTO.setIdMaterial(m.getIdMaterial());
                        materialDTO.setNombreMaterial(m.getNombre());
                        materialDTO.setCantidad(m.getCantidad());
                        materialDTO.setPrecioUnitario(m.getPrecioUnitario().doubleValue());
                        materialDTO.setSubtotal(m.getSubtotal().doubleValue());
                        return materialDTO;
                    })
                    .collect(Collectors.toList());
            }
            if (recibo.getServiciosDetalle() != null) {
                List<ServicioDetalleDTO> serviciosDetalle = objectMapper.readValue(
                    recibo.getServiciosDetalle(), 
                    new TypeReference<List<ServicioDetalleDTO>>() {}
                );
                servicios = serviciosDetalle.stream()
                    .map(s -> {
                        DetalleReciboDTO.ServicioReciboDTO servicioDTO = new DetalleReciboDTO.ServicioReciboDTO();
                        servicioDTO.setIdServicio(s.getIdServicio());
                        servicioDTO.setNombreServicio(s.getNombre());
                        servicioDTO.setDescripcion("Servicio realizado"); // Valor por defecto
                        servicioDTO.setPrecio(s.getPrecio().doubleValue());
                        return servicioDTO;
                    })
                    .collect(Collectors.toList());
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error al convertir JSON de materiales/servicios: " + e.getMessage());
        }
        
        DetalleReciboDTO dto = new DetalleReciboDTO();
        dto.setIdRecibo(recibo.getIdRecibo());
        dto.setIdCotizacion(recibo.getIdCotizacion());
        dto.setFecha(recibo.getFecha().atStartOfDay()); // Convertir LocalDate a LocalDateTime
        dto.setMontoTotal(recibo.getMontoTotal().doubleValue());
        dto.setNombreCliente(recibo.getNombreCliente());
        dto.setPlacaAuto(recibo.getPlacaAuto());
        dto.setMarcaAuto(recibo.getMarcaAuto());
        dto.setModeloAuto(recibo.getModeloAuto());
        dto.setAnioAuto(recibo.getAnioAuto());
        dto.setObservaciones(recibo.getObservaciones());
        dto.setEstadoRecibo(recibo.getEstadoRecibo());
        dto.setFechaCreacion(recibo.getFechaCreacion());
        dto.setSubtotalMateriales(recibo.getSubtotalMateriales().doubleValue());
        dto.setSubtotalServicios(recibo.getSubtotalServicios().doubleValue());
        dto.setMateriales(materiales);
        dto.setServicios(servicios);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<VentasPorServicioDTO> obtenerVentasPorServicio() {
        String sql = "SELECT servicio->>'nombre', SUM((servicio->>'precio')::numeric), COUNT(*) " +
                     "FROM recibo, LATERAL jsonb_array_elements(servicios_detalle::jsonb) AS servicio " +
                     "GROUP BY servicio->>'nombre' ORDER BY SUM((servicio->>'precio')::numeric) DESC";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<VentasPorServicioDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            lista.add(new VentasPorServicioDTO(
                (String) row[0],
                row[1] != null ? ((Number) row[1]).doubleValue() : 0,
                row[2] != null ? ((Number) row[2]).longValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<ServiciosMasVendidosMesDTO> obtenerServiciosMasVendidosMes() {
        String sql = "SELECT DATE_TRUNC('month', r.fecha), servicio->>'nombre', COUNT(*), SUM((servicio->>'precio')::numeric) " +
                     "FROM recibo r, LATERAL jsonb_array_elements(r.servicios_detalle::jsonb) AS servicio " +
                     "WHERE DATE_TRUNC('month', r.fecha) = DATE_TRUNC('month', CURRENT_DATE) " +
                     "GROUP BY DATE_TRUNC('month', r.fecha), servicio->>'nombre' " +
                     "ORDER BY COUNT(*) DESC, SUM((servicio->>'precio')::numeric) DESC";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<ServiciosMasVendidosMesDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            Object dateObj = row[0];
            LocalDate mes;
            if (dateObj instanceof java.sql.Timestamp) {
                mes = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof java.sql.Date) {
                mes = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.Instant) {
                mes = ((java.time.Instant) dateObj).atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                mes = (LocalDate) dateObj;
            } else if (dateObj instanceof java.time.LocalDateTime) {
                mes = ((java.time.LocalDateTime) dateObj).toLocalDate();
            } else {
                throw new IllegalArgumentException("Tipo de fecha no soportado: " + dateObj.getClass());
            }
            lista.add(new ServiciosMasVendidosMesDTO(
                mes,
                (String) row[1],
                row[2] != null ? ((Number) row[2]).longValue() : 0,
                row[3] != null ? ((Number) row[3]).doubleValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<MaterialesMasVendidosMesDTO> obtenerMaterialesMasVendidosMes() {
        String sql = "SELECT DATE_TRUNC('month', r.fecha), material->>'nombre', SUM(COALESCE((material->>'cantidad')::int, 1)), " +
                     "SUM(COALESCE((material->>'precioUnitario')::numeric, 0) * COALESCE((material->>'cantidad')::int, 1)) " +
                     "FROM recibo r, LATERAL jsonb_array_elements(r.materiales_detalle::jsonb) AS material " +
                     "WHERE DATE_TRUNC('month', r.fecha) = DATE_TRUNC('month', CURRENT_DATE) " +
                     "AND material->>'nombre' IS NOT NULL " +
                     "GROUP BY DATE_TRUNC('month', r.fecha), material->>'nombre' " +
                     "ORDER BY SUM(COALESCE((material->>'cantidad')::int, 1)) DESC, SUM(COALESCE((material->>'precioUnitario')::numeric, 0) * COALESCE((material->>'cantidad')::int, 1)) DESC";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<MaterialesMasVendidosMesDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            Object dateObj = row[0];
            LocalDate mes;
            if (dateObj instanceof java.sql.Timestamp) {
                mes = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof java.sql.Date) {
                mes = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.Instant) {
                mes = ((java.time.Instant) dateObj).atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                mes = (LocalDate) dateObj;
            } else if (dateObj instanceof java.time.LocalDateTime) {
                mes = ((java.time.LocalDateTime) dateObj).toLocalDate();
            } else {
                throw new IllegalArgumentException("Tipo de fecha no soportado: " + dateObj.getClass());
            }
            lista.add(new MaterialesMasVendidosMesDTO(
                mes,
                (String) row[1],
                row[2] != null ? ((Number) row[2]).longValue() : 0,
                row[3] != null ? ((Number) row[3]).doubleValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<IngresosPorMesDTO> obtenerIngresosPorMes() {
        String sql = "SELECT DATE_TRUNC('month', fecha), SUM(monto_total) FROM recibo GROUP BY DATE_TRUNC('month', fecha) ORDER BY DATE_TRUNC('month', fecha)";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<IngresosPorMesDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            Object dateObj = row[0];
            LocalDate mes;
            if (dateObj instanceof java.sql.Timestamp) {
                mes = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof java.sql.Date) {
                mes = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.Instant) {
                mes = ((java.time.Instant) dateObj).atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                mes = (LocalDate) dateObj;
            } else if (dateObj instanceof java.time.LocalDateTime) {
                mes = ((java.time.LocalDateTime) dateObj).toLocalDate();
            } else {
                throw new IllegalArgumentException("Tipo de fecha no soportado: " + dateObj.getClass());
            }
            lista.add(new IngresosPorMesDTO(
                mes,
                row[1] != null ? ((Number) row[1]).doubleValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<IngresosPorDiaDTO> obtenerIngresosPorDia() {
        String sql = "SELECT fecha, SUM(monto_total) FROM recibo GROUP BY fecha ORDER BY fecha DESC";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<IngresosPorDiaDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            Object dateObj = row[0];
            LocalDate fecha;
            if (dateObj instanceof java.sql.Timestamp) {
                fecha = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof java.sql.Date) {
                fecha = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.Instant) {
                fecha = ((java.time.Instant) dateObj).atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                fecha = (LocalDate) dateObj;
            } else if (dateObj instanceof java.time.LocalDateTime) {
                fecha = ((java.time.LocalDateTime) dateObj).toLocalDate();
            } else {
                throw new IllegalArgumentException("Tipo de fecha no soportado: " + dateObj.getClass());
            }
            lista.add(new IngresosPorDiaDTO(
                fecha,
                row[1] != null ? ((Number) row[1]).doubleValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<TopClientesDTO> obtenerTopClientes() {
        String sql = "SELECT nombre_cliente, SUM(monto_total) FROM recibo GROUP BY nombre_cliente ORDER BY SUM(monto_total) DESC LIMIT 10";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<TopClientesDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            lista.add(new TopClientesDTO(
                (String) row[0],
                row[1] != null ? ((Number) row[1]).doubleValue() : 0
            ));
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<PromedioSatisfaccionPorTecnicoDTO> obtenerPromedioSatisfaccionPorTecnico() {
        String sql = "SELECT r.nombre_tecnico, AVG(e.promedio_satisfaccion) AS promedio " +
                     "FROM encuesta e JOIN recibo r ON e.id_recibo = r.id_recibo " +
                     "GROUP BY r.nombre_tecnico ORDER BY promedio DESC";
        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
        List<PromedioSatisfaccionPorTecnicoDTO> lista = new ArrayList<>();
        for (Object[] row : results) {
            lista.add(new PromedioSatisfaccionPorTecnicoDTO(
                (String) row[0],
                row[1] != null ? ((Number) row[1]).doubleValue() : 0
            ));
        }
        return lista;
    }
} 