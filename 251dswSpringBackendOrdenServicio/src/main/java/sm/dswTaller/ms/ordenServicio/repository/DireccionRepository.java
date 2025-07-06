package sm.dswTaller.ms.ordenServicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.ordenServicio.model.Direccion;


@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    
}