package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.OrdenPregunta;
import sm.dswTaller.ms.tallerAutomotriz.model.OrdenPreguntaPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenPreguntaRepository extends JpaRepository<OrdenPregunta, OrdenPreguntaPK> {

    public void deleteByIdIdOst(int id);
    List<OrdenPregunta> findByOstIdOst(Integer idOst);
}
