package sm.dswTaller.ms.tallerAutomotriz.controller;

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
import org.springframework.web.bind.annotation.RestController;

import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioMDTO;
import sm.dswTaller.ms.tallerAutomotriz.service.UsuarioService;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.UsuarioRequest;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;

@RestController
@RequestMapping(path = "api/v1/usuario")
public class UsuarioController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<?> getUsuarios(){
        List<UsuarioResponse> listaUsuarioResponse=null;
        try{
            listaUsuarioResponse=usuarioService.listUsuarios();
        
        }catch(Exception e){
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(listaUsuarioResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("usuario not found").build());
        return ResponseEntity.ok(listaUsuarioResponse);
    }
    @PostMapping
    public ResponseEntity<?> insertUsuario(@RequestBody UsuarioRequest usuarioRequest){
        logger.info(">insert"+usuarioRequest.toString());
        UsuarioResponse usuarioResponse;
        try{
            usuarioResponse=usuarioService.insertUsuario(usuarioRequest);
            
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(usuarioResponse==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("usuario not insert").build());
        }
        return ResponseEntity.ok(usuarioResponse);     
    }
    @PutMapping()
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioRequest usuarioRequest){ 
        logger.info(">update" + usuarioRequest.toString());
        UsuarioResponse usuarioResponse;
        try{
            usuarioResponse=usuarioService.findUsuario(usuarioRequest.getIdUsuario());
            if(usuarioResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("usuario not found").build());
            usuarioResponse=usuarioService.updateUsuario(usuarioRequest);
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(usuarioResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("Usuario not update").build());
        return ResponseEntity.ok(usuarioResponse);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteUsuario(@RequestBody UsuarioRequest usuarioRequest){
        logger.info(">delete" + usuarioRequest.toString());
        UsuarioResponse usuarioResponse;
        try{
            usuarioResponse=usuarioService.findUsuario(usuarioRequest.getIdUsuario());
            if(usuarioResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("usuario not found for delete").build());
            usuarioService.deleteUsuario(usuarioRequest.getIdUsuario());
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }
        return ResponseEntity.ok(usuarioResponse);        
    }
    @GetMapping("/find")
    public ResponseEntity<?> findUsuarioById(@RequestBody UsuarioRequest usuarioRequest){
      logger.info(">find" + usuarioRequest.toString());
        UsuarioResponse usuarioResponse;
        try{
            usuarioResponse=usuarioService.findUsuario(usuarioRequest.getIdUsuario());
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(usuarioResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("usuario not found").build());
        return ResponseEntity.ok(usuarioResponse);      
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Long id) {
        logger.info(">find id=" + id);
        UsuarioResponse usuarioResponse;
        try {
            usuarioResponse = usuarioService.findUsuario(id);
        } catch (Exception e) {
            logger.error("error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuarioResponse == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("usuario not found").build());
        return ResponseEntity.ok(usuarioResponse);
    }

    


    @GetMapping("/{idUsuario}/persona")
    public ResponseEntity<Long> obtenerIdPersonaPorUsuario(@PathVariable Long idUsuario) {
        Long idPersona = usuarioService.obtenerIdPersonaPorUsuario(idUsuario);
        if (idPersona != null) {
            return ResponseEntity.ok(idPersona);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("mini/{id}")
    public ResponseEntity<?> getUsuarioMiniById(@PathVariable Long id){
        UsuarioMDTO dto = usuarioService.getUsuarioMiniById(id);
        return ResponseEntity.ok(dto);
    }

}   
