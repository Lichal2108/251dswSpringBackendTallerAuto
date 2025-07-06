package sm.dswTaller.ms.tallerVentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.tallerVentas.model.ReciboOrdenEvaluacion;

import java.util.List;

@Repository
public interface ReciboOrdenEvaluacionRepository extends JpaRepository<ReciboOrdenEvaluacion, Long> {
    List<ReciboOrdenEvaluacion> findByIdRecibo(Long idRecibo);
} 