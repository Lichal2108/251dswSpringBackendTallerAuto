package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.InventarioAuto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioAutoRepository extends JpaRepository<InventarioAuto, Long> {
    List<InventarioAuto> findByOst_IdOst(Long idOst);
}
