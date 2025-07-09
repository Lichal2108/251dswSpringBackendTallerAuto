package sm.dswTaller.ms.ordenServicio.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.ordenServicio.dto.InventarioByOstDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstMsResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstResponseDTO;
import sm.dswTaller.ms.ordenServicio.service.OstServiceImp;

@RestController
@RequestMapping(path = "api/v1/ost")
public class OstController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OstServiceImp ostService;
    
    @GetMapping
    public ResponseEntity<?> getOsts(){
        List<OstResponseDTO> listaOstResponseDTO;
        try{
            listaOstResponseDTO = ostService.listOsts();
        
        }catch(Exception e){
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(listaOstResponseDTO);
        
    }
    @GetMapping("/mis-ost/{idUsuario}")
    public ResponseEntity<?> obtenerMisOst(@PathVariable Long idUsuario) {
        List<OstResponseDTO> lista = ostService.buscarOstPorIdPersona(idUsuario);
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/supervisor/{idSupervisor}")
    public ResponseEntity<?> obtenerPorSupervisor(@PathVariable Long idSupervisor) {
        List<OstResponseDTO> lista = ostService.obtenerOstPorSupervisor(idSupervisor);
        return ResponseEntity.ok(lista);
    }
    
    @PostMapping
    public ResponseEntity<?> insertOst(@RequestBody OstRequestDTO ostRequestDTO){
        logger.info(">insert"+ostRequestDTO.toString());
        OstMsResponseDTO ostResponseDTO;
        try{
            ostResponseDTO=ostService.insertOst(ostRequestDTO);
            
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }
        return ResponseEntity.ok(ostResponseDTO); 
        
    }
    @PutMapping()
    public ResponseEntity<?> updateOst(@RequestBody OstRequestDTO ostRequestDTO){ 
        logger.info(">update" + ostRequestDTO.toString());
        OstResponseDTO ostResponseDTO;
        try{
            ostResponseDTO=ostService.findOst(ostRequestDTO.getIdOst());
            /*if(ostResponseDTO==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("ost not found").build());*/
            ostResponseDTO=ostService.updateOst(ostRequestDTO);
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }
        return ResponseEntity.ok(ostResponseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOst(@PathVariable Long id) {
        try {
            ostService.deleteOst(id);
            return ResponseEntity.ok("OST eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/api/v1/ost/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam Integer idEstado) {
        ostService.actualizarEstado(id, idEstado);
        return ResponseEntity.ok().build();
    }
    /*    @PutMapping("/actualizar-inventario/{idOst}")
    public ResponseEntity<?> actualizarInventario(@PathVariable Integer idOst,
    @RequestBody InventarioByOstDTO dto) {
    ostService.actualizarInventarioYRevision(idOst, dto);
    return ResponseEntity.ok("Inventario y datos de revisión actualizados.");
    }*/
}
