package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoDocumentoRepository  extends JpaRepository<TipoDocumento, Integer>{
    
    Optional<TipoDocumento> findByTipoDoc(String tipoDocumento); //nombre-cliente
    
}
