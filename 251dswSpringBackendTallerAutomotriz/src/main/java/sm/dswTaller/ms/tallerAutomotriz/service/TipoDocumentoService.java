
package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoDocumento;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {
    @Autowired
    TipoDocumentoRepository TipoDocumentoRepository;
    public List<TipoDocumento> getTipoDocumentos(){
        return TipoDocumentoRepository.findAll();
    }
    
}
