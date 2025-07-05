package sm.dswTaller.ms.tallerAutomotriz.service;

import sm.dswTaller.ms.tallerAutomotriz.model.Direccion;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.DireccionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {


    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }
    
}
