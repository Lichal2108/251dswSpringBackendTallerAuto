package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
        boolean existsByNroDocumento(String nroDocumento);
        Optional<Persona>  findByNombresContainingIgnoreCase(String filtro);
        Optional<Persona> findByNroDocumento(String documento);
}
