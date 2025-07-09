package sm.dswTaller.ms.ordenServicio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sm.dswTaller.ms.ordenServicio.model.InventarioAuto;

public interface InventarioAutoRepository extends JpaRepository<InventarioAuto, Long> {
    List<InventarioAuto> findByOst_IdOst(Long idOst);
}
