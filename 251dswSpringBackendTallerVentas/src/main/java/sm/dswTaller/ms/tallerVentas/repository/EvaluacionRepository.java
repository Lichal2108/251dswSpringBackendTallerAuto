package sm.dswTaller.ms.tallerVentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.tallerVentas.model.Evaluacion;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    
    /**
     * Obtener todas las evaluaciones de una encuesta
     */
    List<Evaluacion> findByIdEncuesta(Long idEncuesta);
    
    /**
     * Obtener evaluaciones por pregunta
     */
    List<Evaluacion> findByIdPregunta(Long idPregunta);
} 