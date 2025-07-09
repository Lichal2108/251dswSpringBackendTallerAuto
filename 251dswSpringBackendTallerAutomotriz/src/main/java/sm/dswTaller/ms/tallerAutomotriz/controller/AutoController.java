/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoDTO;
import sm.dswTaller.ms.tallerAutomotriz.service.AutoService;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoRequest;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;

@RestController
@RequestMapping("/api/v1/auto")
public class AutoController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private AutoService autoService;
    @Autowired
    PersonaRepository personaRepository;
    @GetMapping("/autos/persona/{idPersona}")
    public List<AutoDTO> getAutosPorPersona(@PathVariable Integer idPersona) {
        System.out.println("autitosss");
        return autoService.findByPersona(idPersona);
    }
    @PostMapping
    public ResponseEntity<?> insertAuto(@RequestBody AutoRequest autoRequest){
        logger.info(">insert"+autoRequest.toString());
        AutoResponse autoResponse;
        try{
            autoResponse=autoService.insertAuto(autoRequest);
            
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(autoResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("AUTO not insert").build());
        return ResponseEntity.ok(autoResponse);     
    }
    @PutMapping()
    public ResponseEntity<?> updateAuto(@RequestBody AutoRequest autoRequest){
        logger.info(">update"+autoRequest.toString());
        AutoResponse autoResponse;
        try{
            autoResponse=autoService.updateAuto(autoRequest);
            
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(autoResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("AUTO not insert").build());
        return ResponseEntity.ok(autoResponse);     
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteAuto(@RequestBody AutoRequest autoRequest){
        logger.info(">delete" + autoRequest.toString());
        AutoResponse autoResponse;
        try{
            autoResponse=autoService.findAuto(autoRequest.getIdAuto());
            if(autoResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("auto not found for delete").build());
            autoService.deleteAuto(autoRequest.getIdAuto());
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }
        return ResponseEntity.ok(autoResponse);
       
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAutoById(@PathVariable Integer id) {
        AutoDTO dto = autoService.getAutoById(id);
        return ResponseEntity.ok(dto);
    }
}
