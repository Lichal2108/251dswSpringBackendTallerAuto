package sm.dswTaller.ms.ordenServicio.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.model.Direccion;
import sm.dswTaller.ms.ordenServicio.repository.DireccionRepository;

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
