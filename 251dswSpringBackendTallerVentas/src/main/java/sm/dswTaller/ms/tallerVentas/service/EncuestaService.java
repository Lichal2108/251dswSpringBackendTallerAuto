package sm.dswTaller.ms.tallerVentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerVentas.model.Encuesta;
import sm.dswTaller.ms.tallerVentas.repository.EncuestaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EncuestaService {
    
    @Autowired
    private EncuestaRepository encuestaRepository;
    
    /**
     * Crear una nueva encuesta
     */
    public Encuesta createEncuesta(Encuesta encuesta) {
        return encuestaRepository.save(encuesta);
    }
    
    /**
     * Obtener encuesta por ID
     */
    public Optional<Encuesta> getEncuestaById(Long idEncuesta) {
        return encuestaRepository.findById(idEncuesta);
    }
    
    /**
     * Obtener encuesta por ID de recibo
     */
    public Optional<Encuesta> getEncuestaByIdRecibo(Long idRecibo) {
        return encuestaRepository.findByIdRecibo(idRecibo);
    }
    
    /**
     * Verificar si existe una encuesta para un recibo
     */
    public boolean existsByIdRecibo(Long idRecibo) {
        return encuestaRepository.existsByIdRecibo(idRecibo);
    }
    
    /**
     * Obtener todas las encuestas de un cliente
     */
    public List<Encuesta> getEncuestasByIdCliente(Long idCliente) {
        return encuestaRepository.findByIdCliente(idCliente);
    }
    
    /**
     * Obtener todas las encuestas
     */
    public List<Encuesta> getAllEncuestas() {
        return encuestaRepository.findAll();
    }
    
    /**
     * Actualizar una encuesta
     */
    public Encuesta updateEncuesta(Encuesta encuesta) {
        return encuestaRepository.save(encuesta);
    }
    
    /**
     * Eliminar una encuesta
     */
    public void deleteEncuesta(Long idEncuesta) {
        encuestaRepository.deleteById(idEncuesta);
    }
} 