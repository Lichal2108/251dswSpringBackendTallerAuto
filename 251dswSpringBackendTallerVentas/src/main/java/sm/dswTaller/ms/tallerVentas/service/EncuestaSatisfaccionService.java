package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.dswTaller.ms.tallerVentas.dto.*;
import sm.dswTaller.ms.tallerVentas.model.Evaluacion;
import sm.dswTaller.ms.tallerVentas.model.ReciboOrdenEvaluacion;
import sm.dswTaller.ms.tallerVentas.repository.EvaluacionRepository;
import sm.dswTaller.ms.tallerVentas.repository.ReciboOrdenEvaluacionRepository;
import sm.dswTaller.ms.tallerVentas.repository.ReciboRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncuestaSatisfaccionService {
    
    @Autowired
    private EvaluacionService evaluacionService;
    
    @Autowired
    private ReciboOrdenEvaluacionService reciboOrdenEvaluacionService;
    
    @Autowired
    private ReciboRepository reciboRepository;
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private ReciboOrdenEvaluacionRepository reciboOrdenEvaluacionRepository;
    
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
        
        // Crear todas las evaluaciones
        List<EvaluacionResponseDTO> evaluacionesCreadas = request.getEvaluaciones().stream()
                .map(evaluacionService::createEvaluacion)
                .collect(Collectors.toList());
        
        // Calcular promedio de satisfacción
        BigDecimal promedioSatisfaccion = calcularPromedioSatisfaccion(evaluacionesCreadas);
        
        // Crear la relación recibo-orden-evaluación
        // Por simplicidad, usamos la primera evaluación como representativa
        // En un caso real, podrías querer crear múltiples registros o una estructura diferente
        Long idEvaluacion = evaluacionesCreadas.get(0).getIdEvaluacion();
        
        ReciboOrdenEvaluacionRequestDTO reciboOrdenEvaluacionRequest = new ReciboOrdenEvaluacionRequestDTO(
                request.getIdRecibo(),
                request.getIdCotizacion(),
                idEvaluacion
        );
        
        ReciboOrdenEvaluacionResponseDTO reciboOrdenEvaluacion = reciboOrdenEvaluacionService.createReciboOrdenEvaluacion(reciboOrdenEvaluacionRequest);
        
        return new EncuestaSatisfaccionResponseDTO(
                reciboOrdenEvaluacion.getId(),
                request.getIdRecibo(),
                request.getIdCotizacion(),
                evaluacionesCreadas,
                promedioSatisfaccion,
                "Encuesta de satisfacción procesada exitosamente"
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
        // Buscar la relación recibo-orden-evaluación
        List<ReciboOrdenEvaluacion> relaciones = reciboOrdenEvaluacionRepository.findByIdRecibo(idRecibo);
        
        if (relaciones.isEmpty()) {
            throw new IllegalArgumentException("No se encontró encuesta para el recibo especificado");
        }
        
        // Obtener todas las evaluaciones relacionadas
        List<EvaluacionResponseDTO> evaluaciones = relaciones.stream()
                .map(relacion -> evaluacionService.getEvaluacionById(relacion.getIdEvaluacion()))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toList());
        
        BigDecimal promedioSatisfaccion = calcularPromedioSatisfaccion(evaluaciones);
        
        return new EncuestaSatisfaccionResponseDTO(
                relaciones.get(0).getId(),
                idRecibo,
                relaciones.get(0).getIdCotizacion(),
                evaluaciones,
                promedioSatisfaccion,
                "Encuesta de satisfacción recuperada exitosamente"
        );
    }
} 