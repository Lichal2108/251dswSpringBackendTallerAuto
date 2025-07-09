package sm.dswTaller.ms.tallerVentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.tallerVentas.model.Encuesta;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
    
    /**
     * Buscar encuesta por ID de recibo
     */
    Optional<Encuesta> findByIdRecibo(Long idRecibo);
    
    /**
     * Verificar si existe una encuesta para un recibo
     */
    boolean existsByIdRecibo(Long idRecibo);
    
    /**
     * Obtener todas las encuestas de un cliente (a través de la relación con recibo)
     */
    @Query("SELECT e FROM Encuesta e JOIN Recibo r ON e.idRecibo = r.idRecibo WHERE r.idCliente = :idCliente")
    List<Encuesta> findByIdCliente(@Param("idCliente") Long idCliente);
} 