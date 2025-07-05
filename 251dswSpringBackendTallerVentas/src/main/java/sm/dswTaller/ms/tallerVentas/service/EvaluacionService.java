package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.model.Evaluacion;
import sm.dswTaller.ms.tallerVentas.repository.EvaluacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    public List<EvaluacionResponseDTO> getAllEvaluaciones() {
        return evaluacionRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<EvaluacionResponseDTO> getEvaluacionById(Long id) {
        return evaluacionRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public EvaluacionResponseDTO createEvaluacion(EvaluacionRequestDTO request) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setPuntajeSatisfaccion(request.getPuntajeSatisfaccion());
        
        Evaluacion savedEvaluacion = evaluacionRepository.save(evaluacion);
        return convertToResponse(savedEvaluacion);
    }
    
    public Optional<EvaluacionResponseDTO> updateEvaluacion(Long id, EvaluacionRequestDTO request) {
        return evaluacionRepository.findById(id)
                .map(evaluacion -> {
                    evaluacion.setPuntajeSatisfaccion(request.getPuntajeSatisfaccion());
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
        return new EvaluacionResponseDTO(
                evaluacion.getIdEvaluacion(),
                evaluacion.getPuntajeSatisfaccion()
        );
    }
} 