package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.model.Evaluacion;
import sm.dswTaller.ms.tallerVentas.model.PreguntasEvaluacion;
import sm.dswTaller.ms.tallerVentas.repository.EvaluacionRepository;
import sm.dswTaller.ms.tallerVentas.repository.PreguntasEvaluacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private PreguntasEvaluacionRepository preguntasEvaluacionRepository;
    
    public List<EvaluacionResponseDTO> getAllEvaluaciones() {
        return evaluacionRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<EvaluacionResponseDTO> getEvaluacionById(Long id) {
        return evaluacionRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public List<EvaluacionResponseDTO> getEvaluacionesByIdEncuesta(Long idEncuesta) {
        return evaluacionRepository.findByIdEncuesta(idEncuesta).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public EvaluacionResponseDTO createEvaluacion(EvaluacionRequestDTO request, Long idEncuesta) {
        // Validar que el puntaje esté entre 1 y 5
        if (request.getPuntajeSatisfaccion() < 1 || request.getPuntajeSatisfaccion() > 5) {
            throw new IllegalArgumentException("El puntaje de satisfacción debe estar entre 1 y 5");
        }
        
        // Validar que la pregunta existe
        if (!preguntasEvaluacionRepository.existsById(request.getIdPregunta())) {
            throw new IllegalArgumentException("La pregunta especificada no existe");
        }
        
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setIdEncuesta(idEncuesta);
        evaluacion.setIdPregunta(request.getIdPregunta());
        evaluacion.setPuntajeSatisfaccion(request.getPuntajeSatisfaccion());
        evaluacion.setComentario(request.getComentario());
        
        Evaluacion savedEvaluacion = evaluacionRepository.save(evaluacion);
        return convertToResponse(savedEvaluacion);
    }
    
    public Optional<EvaluacionResponseDTO> updateEvaluacion(Long id, EvaluacionRequestDTO request) {
        return evaluacionRepository.findById(id)
                .map(evaluacion -> {
                    // Validar que el puntaje esté entre 1 y 5
                    if (request.getPuntajeSatisfaccion() < 1 || request.getPuntajeSatisfaccion() > 5) {
                        throw new IllegalArgumentException("El puntaje de satisfacción debe estar entre 1 y 5");
                    }
                    
                    evaluacion.setIdPregunta(request.getIdPregunta());
                    evaluacion.setPuntajeSatisfaccion(request.getPuntajeSatisfaccion());
                    evaluacion.setComentario(request.getComentario());
                    
                    Evaluacion savedEvaluacion = evaluacionRepository.save(evaluacion);
                    return convertToResponse(savedEvaluacion);
                });
    }
    
    public boolean deleteEvaluacion(Long id) {
        if (evaluacionRepository.existsById(id)) {
            evaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private EvaluacionResponseDTO convertToResponse(Evaluacion evaluacion) {
        // Obtener la pregunta para incluirla en la respuesta
        String pregunta = "";
        Optional<PreguntasEvaluacion> preguntaOpt = preguntasEvaluacionRepository.findById(evaluacion.getIdPregunta());
        if (preguntaOpt.isPresent()) {
            pregunta = preguntaOpt.get().getPregunta();
        }
        
        return new EvaluacionResponseDTO(
                evaluacion.getIdEvaluacion(),
                evaluacion.getIdEncuesta(),
                evaluacion.getIdPregunta(),
                pregunta,
                evaluacion.getPuntajeSatisfaccion(),
                evaluacion.getComentario()
        );
    }
} 