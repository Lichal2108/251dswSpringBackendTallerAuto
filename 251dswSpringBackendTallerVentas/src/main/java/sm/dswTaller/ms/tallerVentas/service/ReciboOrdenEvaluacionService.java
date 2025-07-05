package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.dto.ReciboOrdenEvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboOrdenEvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.model.ReciboOrdenEvaluacion;
import sm.dswTaller.ms.tallerVentas.repository.ReciboOrdenEvaluacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReciboOrdenEvaluacionService {
    
    @Autowired
    private ReciboOrdenEvaluacionRepository reciboOrdenEvaluacionRepository;
    
    public List<ReciboOrdenEvaluacionResponseDTO> getAllReciboOrdenEvaluacion() {
        return reciboOrdenEvaluacionRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Optional<ReciboOrdenEvaluacionResponseDTO> getReciboOrdenEvaluacionById(Long id) {
        return reciboOrdenEvaluacionRepository.findById(id)
                .map(this::convertToResponse);
    }
    
    public ReciboOrdenEvaluacionResponseDTO createReciboOrdenEvaluacion(ReciboOrdenEvaluacionRequestDTO request) {
        ReciboOrdenEvaluacion reciboOrdenEvaluacion = new ReciboOrdenEvaluacion();
        reciboOrdenEvaluacion.setIdRecibo(request.getIdRecibo());
        reciboOrdenEvaluacion.setIdCotizacion(request.getIdCotizacion());
        reciboOrdenEvaluacion.setIdEvaluacion(request.getIdEvaluacion());
        
        ReciboOrdenEvaluacion savedReciboOrdenEvaluacion = reciboOrdenEvaluacionRepository.save(reciboOrdenEvaluacion);
        return convertToResponse(savedReciboOrdenEvaluacion);
    }
    
    public Optional<ReciboOrdenEvaluacionResponseDTO> updateReciboOrdenEvaluacion(Long id, ReciboOrdenEvaluacionRequestDTO request) {
        return reciboOrdenEvaluacionRepository.findById(id)
                .map(reciboOrdenEvaluacion -> {
                    reciboOrdenEvaluacion.setIdRecibo(request.getIdRecibo());
                    reciboOrdenEvaluacion.setIdCotizacion(request.getIdCotizacion());
                    reciboOrdenEvaluacion.setIdEvaluacion(request.getIdEvaluacion());
                    ReciboOrdenEvaluacion savedReciboOrdenEvaluacion = reciboOrdenEvaluacionRepository.save(reciboOrdenEvaluacion);
                    return convertToResponse(savedReciboOrdenEvaluacion);
                });
    }
    
    public boolean deleteReciboOrdenEvaluacion(Long id) {
        if (reciboOrdenEvaluacionRepository.existsById(id)) {
            reciboOrdenEvaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private ReciboOrdenEvaluacionResponseDTO convertToResponse(ReciboOrdenEvaluacion reciboOrdenEvaluacion) {
        return new ReciboOrdenEvaluacionResponseDTO(
                reciboOrdenEvaluacion.getId(),
                reciboOrdenEvaluacion.getIdRecibo(),
                reciboOrdenEvaluacion.getIdCotizacion(),
                reciboOrdenEvaluacion.getIdEvaluacion()
        );
    }
} 