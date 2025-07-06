package sm.dswTaller.ms.tallerVentas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboResponseDTO;
import sm.dswTaller.ms.tallerVentas.dto.MaterialDetalleDTO;
import sm.dswTaller.ms.tallerVentas.dto.ServicioDetalleDTO;
import sm.dswTaller.ms.tallerVentas.model.Recibo;
import sm.dswTaller.ms.tallerVentas.repository.ReciboRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReciboService {
    
    @Autowired
    private ReciboRepository reciboRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
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
} 