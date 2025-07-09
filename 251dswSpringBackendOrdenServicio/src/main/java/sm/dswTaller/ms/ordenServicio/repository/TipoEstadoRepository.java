package sm.dswTaller.ms.ordenServicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.ordenServicio.model.TipoEstado;

/**
 *
 * @author Aldair
 */
@Repository
public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Integer> {

    public TipoEstado findByEstadoIgnoreCase(String atendida);
    
}
