package sm.dswTaller.ms.ordenServicio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sm.dswTaller.ms.ordenServicio.model.OrdenPregunta;
import sm.dswTaller.ms.ordenServicio.model.OrdenPreguntaPK;


public interface OrdenPreguntaRepository extends JpaRepository<OrdenPregunta, OrdenPreguntaPK> {

    public void deleteByIdIdOst(Long id);
    List<OrdenPregunta> findByOstIdOst(Long idOst);
}
