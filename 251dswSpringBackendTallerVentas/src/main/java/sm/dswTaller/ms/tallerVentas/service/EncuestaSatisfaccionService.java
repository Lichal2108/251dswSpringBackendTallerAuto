package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.dswTaller.ms.tallerVentas.dto.*;
import sm.dswTaller.ms.tallerVentas.model.Encuesta;
import sm.dswTaller.ms.tallerVentas.model.Evaluacion;
import sm.dswTaller.ms.tallerVentas.model.Recibo;
import sm.dswTaller.ms.tallerVentas.repository.EncuestaRepository;
import sm.dswTaller.ms.tallerVentas.repository.EvaluacionRepository;
import sm.dswTaller.ms.tallerVentas.repository.ReciboRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EncuestaSatisfaccionService {
    
    @Autowired
    private EvaluacionService evaluacionService;
    
    @Autowired
    private EncuestaService encuestaService;
    
    @Autowired
    private ReciboRepository reciboRepository;
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Transactional
    public EncuestaSatisfaccionResponseDTO procesarEncuestaSatisfaccion(EncuestaSatisfaccionRequestDTO request) {
        // Validar que el recibo existe
        if (!reciboRepository.existsById(request.getIdRecibo())) {
            throw new IllegalArgumentException("El recibo especificado no existe");
        }
        
        // Validar que hay evaluaciones
        if (request.getEvaluaciones() == null || request.getEvaluaciones().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos una evaluación");
        }
        
        // Crear la encuesta principal
        Encuesta encuesta = new Encuesta();
        encuesta.setIdRecibo(request.getIdRecibo());
        encuesta.setIdCotizacion(request.getIdCotizacion());
        encuesta.setComentarioGeneral("Encuesta de satisfacción completada");
        
        Encuesta encuestaGuardada = encuestaService.createEncuesta(encuesta);
        
        // Crear todas las evaluaciones asociadas a la encuesta
        List<EvaluacionResponseDTO> evaluacionesCreadas = request.getEvaluaciones().stream()
                .map(evaluacionRequest -> evaluacionService.createEvaluacion(evaluacionRequest, encuestaGuardada.getIdEncuesta()))
                .collect(Collectors.toList());
        
        // Calcular promedio de satisfacción
        BigDecimal promedioSatisfaccion = calcularPromedioSatisfaccion(evaluacionesCreadas);
        
        // Actualizar la encuesta con el promedio
        encuestaGuardada.setPromedioSatisfaccion(promedioSatisfaccion.doubleValue());
        encuestaService.updateEncuesta(encuestaGuardada);

        // Cambiar el estado del recibo a 'ENCUESTA REALIZADA'
        Recibo recibo = reciboRepository.findById(request.getIdRecibo()).orElseThrow(() -> new IllegalArgumentException("El recibo especificado no existe"));
        recibo.setEstadoRecibo("ENCUESTA REALIZADA");
        reciboRepository.save(recibo);
        
        return new EncuestaSatisfaccionResponseDTO(
                encuestaGuardada.getIdEncuesta(),
                request.getIdRecibo(),
                request.getIdCotizacion(),
                evaluacionesCreadas,
                promedioSatisfaccion,
                "Encuesta de satisfacción procesada exitosamente",
                recibo.getEstadoRecibo(),
                recibo.getIdCliente()
        );
    }
    
    private BigDecimal calcularPromedioSatisfaccion(List<EvaluacionResponseDTO> evaluaciones) {
        if (evaluaciones.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        int sumaPuntajes = evaluaciones.stream()
                .mapToInt(EvaluacionResponseDTO::getPuntajeSatisfaccion)
                .sum();
        
        BigDecimal promedio = BigDecimal.valueOf(sumaPuntajes)
                .divide(BigDecimal.valueOf(evaluaciones.size()), 2, RoundingMode.HALF_UP);
        
        return promedio;
    }
    
    public EncuestaSatisfaccionResponseDTO obtenerEncuestaPorRecibo(Long idRecibo) {
        // Buscar la encuesta por ID de recibo
        Optional<Encuesta> encuestaOpt = encuestaService.getEncuestaByIdRecibo(idRecibo);
        
        if (encuestaOpt.isEmpty()) {
            throw new IllegalArgumentException("No se encontró encuesta para el recibo especificado");
        }
        
        Encuesta encuesta = encuestaOpt.get();
        
        // Obtener todas las evaluaciones de la encuesta
        List<EvaluacionResponseDTO> evaluaciones = evaluacionService.getEvaluacionesByIdEncuesta(encuesta.getIdEncuesta());
        
        BigDecimal promedioSatisfaccion = BigDecimal.valueOf(encuesta.getPromedioSatisfaccion());
        Recibo recibo = reciboRepository.findById(idRecibo).orElse(null);
        String estadoRecibo = recibo != null ? recibo.getEstadoRecibo() : null;
        Long idCliente = recibo != null ? recibo.getIdCliente() : null;
        
        return new EncuestaSatisfaccionResponseDTO(
                encuesta.getIdEncuesta(),
                idRecibo,
                encuesta.getIdCotizacion(),
                evaluaciones,
                promedioSatisfaccion,
                "Encuesta de satisfacción recuperada exitosamente",
                estadoRecibo,
                idCliente
        );
    }
    
    public boolean verificarEncuestaRecibo(Long idRecibo) {
        return encuestaService.existsByIdRecibo(idRecibo);
    }
    
    public List<EncuestaSatisfaccionResponseDTO> obtenerTodasLasEncuestas() {
        List<Encuesta> todasLasEncuestas = encuestaService.getAllEncuestas();
        
        return todasLasEncuestas.stream()
                .map(encuesta -> {
                    try {
                        EncuestaSatisfaccionResponseDTO encuestaResponse = obtenerEncuestaPorRecibo(encuesta.getIdRecibo());
                        // Filtrar solo si el recibo está en estado 'ENCUESTA REALIZADA'
                        Recibo recibo = reciboRepository.findById(encuestaResponse.getIdRecibo()).orElse(null);
                        if (recibo != null && "ENCUESTA REALIZADA".equals(recibo.getEstadoRecibo())) {
                            return encuestaResponse;
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(encuesta -> encuesta != null)
                .collect(Collectors.toList());
    }
    
    public List<EncuestaSatisfaccionResponseDTO> obtenerEncuestasPorCliente(Long idCliente) {
        // Filtrar por cliente y estado 'ENCUESTA REALIZADA'
        List<Recibo> recibosCliente = reciboRepository.findByIdCliente(idCliente);
        List<Long> idsRecibosRealizada = recibosCliente.stream()
                .filter(r -> "ENCUESTA REALIZADA".equals(r.getEstadoRecibo()))
                .map(Recibo::getIdRecibo)
                .collect(Collectors.toList());
        return obtenerTodasLasEncuestas().stream()
                .filter(encuesta -> idsRecibosRealizada.contains(encuesta.getIdRecibo()))
                .collect(Collectors.toList());
    }
    
    public List<CotizacionPendienteEncuestaDTO> obtenerCotizacionesPendientesEncuesta(Long idCliente) {
        // Obtener todos los recibos del cliente que no tienen encuesta
        List<Recibo> recibosCliente = reciboRepository.findByIdCliente(idCliente);
        
        return recibosCliente.stream()
                .filter(recibo -> "ACTIVO".equals(recibo.getEstadoRecibo()))
                .filter(recibo -> !encuestaService.existsByIdRecibo(recibo.getIdRecibo()))
                .map(recibo -> {
                    return new CotizacionPendienteEncuestaDTO(
                            recibo.getIdRecibo(),
                            recibo.getIdCotizacion(),
                            recibo.getFecha().atStartOfDay(), // Convertir LocalDate a LocalDateTime
                            "Servicio automotriz - " + recibo.getObservaciones(),
                            recibo.getMontoTotal(),
                            idCliente,
                            recibo.getNombreCliente(),
                            recibo.getPlacaAuto(),
                            recibo.getMarcaAuto(),
                            recibo.getModeloAuto() + " " + recibo.getAnioAuto(),
                            recibo.getEstadoRecibo(),
                            recibo.getFechaCreacion().plusDays(30) // Fecha de vencimiento
                    );
                })
                .collect(Collectors.toList());
    }
} 