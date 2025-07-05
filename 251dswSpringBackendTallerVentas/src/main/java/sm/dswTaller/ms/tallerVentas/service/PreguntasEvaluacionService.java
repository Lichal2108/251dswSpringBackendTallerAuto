package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.dto.PreguntasEvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.PreguntasEvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.model.PreguntasEvaluacion;
import sm.dswTaller.ms.tallerVentas.repository.PreguntasEvaluacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreguntasEvaluacionService {
    
    @Autowired
    private PreguntasEvaluacionRepository preguntasEvaluacionRepository;
    
    public List<PreguntasEvaluacionResponseDTO> getAllPreguntasEvaluacion() {
        return preguntasEvaluacionRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<PreguntasEvaluacionResponseDTO> getPreguntaEvaluacionById(Long id) {
        return preguntasEvaluacionRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public PreguntasEvaluacionResponseDTO createPreguntaEvaluacion(PreguntasEvaluacionRequestDTO request) {
        PreguntasEvaluacion pregunta = new PreguntasEvaluacion();
        pregunta.setPregunta(request.getPregunta());
        
        PreguntasEvaluacion savedPregunta = preguntasEvaluacionRepository.save(pregunta);
        return convertToResponse(savedPregunta);
    }
    
    public Optional<PreguntasEvaluacionResponseDTO> updatePreguntaEvaluacion(Long id, PreguntasEvaluacionRequestDTO request) {
        return preguntasEvaluacionRepository.findById(id)
                .map(pregunta -> {
                    pregunta.setPregunta(request.getPregunta());
                    PreguntasEvaluacion savedPregunta = preguntasEvaluacionRepository.save(pregunta);
                    return convertToResponse(savedPregunta);
                });
    }
    
    public boolean deletePreguntaEvaluacion(Long id) {
        if (preguntasEvaluacionRepository.existsById(id)) {
            preguntasEvaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private PreguntasEvaluacionResponseDTO convertToResponse(PreguntasEvaluacion pregunta) {
        return new PreguntasEvaluacionResponseDTO(
                pregunta.getId(),
                pregunta.getPregunta()
        );
    }
} 