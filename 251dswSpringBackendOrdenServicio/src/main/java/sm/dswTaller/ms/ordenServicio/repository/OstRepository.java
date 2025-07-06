package sm.dswTaller.ms.ordenServicio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.ordenServicio.model.Ost;

/**
 *
 * @author Ciro
 */
@Repository
public interface OstRepository extends JpaRepository<Ost, Long> {
    public List<Ost> findBySupervisorId(Long idSupervisor);
    public List<Ost> findByRecepcionistaId(Long idCliente);
    @Query("SELECT o FROM Ost o WHERE o.auto.persona.id = :idPersona")
    public List<Ost> findByIdPersonaCliente(Long idPersona);
}
