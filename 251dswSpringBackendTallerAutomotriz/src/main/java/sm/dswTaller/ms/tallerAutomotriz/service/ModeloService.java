
package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import sm.dswTaller.ms.tallerAutomotriz.model.Modelo;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ModeloRepository;

@Service
public class ModeloService {
    @Autowired private ModeloRepository modeloRepo;
    
    public List<Modelo> findAll() {
        return modeloRepo.findAll();
    }
}
