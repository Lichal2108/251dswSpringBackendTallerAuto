
package sm.dswTaller.ms.ordenServicio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sm.dswTaller.ms.ordenServicio.model.OstTecnico;
import sm.dswTaller.ms.ordenServicio.model.OstTecnicoId;

public interface OstTecnicoRepository extends JpaRepository<OstTecnico, OstTecnicoId> {
    Optional<OstTecnico> findById_IdOstAndId_IdTecnico(Long idOst, Long idTecnico);

    List<OstTecnico> findById_IdOst(Long idOst);

    List<OstTecnico> findById_IdTecnico(Long idTecnico);
}
