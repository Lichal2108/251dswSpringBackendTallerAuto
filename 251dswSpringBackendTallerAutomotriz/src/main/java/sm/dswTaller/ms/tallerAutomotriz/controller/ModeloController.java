package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.service.ModeloService;
import sm.dswTaller.ms.tallerAutomotriz.model.Modelo;

@RestController
@RequestMapping("/api/v1/modelo")
public class ModeloController {       

    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @Autowired private ModeloService modeloService;
    
    @GetMapping
    public List<Modelo> getModelos() {
        return modeloService.findAll();
    }
}
