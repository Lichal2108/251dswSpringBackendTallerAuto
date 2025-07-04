package sm.dswTaller.ms.tallerAutomotriz.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.service.TipoSolucionService;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoSolucion;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;

/**
 *
 * @author Ciro
 */
@RestController
@RequestMapping(path = "api/v1/tipoSolucion")
public class TipoSolucionController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    TipoSolucionService tipoSolucionService;
    
    @GetMapping
    public ResponseEntity<?> getTipoSoluciones(){
        List<TipoSolucion> listaTipoSolucion;
        try{
            listaTipoSolucion=tipoSolucionService.getTipoSoluciones();
        
        }catch(Exception e){
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(listaTipoSolucion.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("tipoSolucion not found").build());
        return ResponseEntity.ok(listaTipoSolucion);
    }    
    
}
