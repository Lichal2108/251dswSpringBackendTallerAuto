package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import java.util.Optional;
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
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoDTO;
import sm.dswTaller.ms.tallerAutomotriz.service.PersonaService;
import sm.dswTaller.ms.tallerAutomotriz.service.AutoService;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;
import sm.dswTaller.ms.tallerAutomotriz.dto.PersonaRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.PersonaResponse;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoResponse;

@RestController
@RequestMapping(path = "api/v1/persona")
public class PersonaController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PersonaService personaService;
    @Autowired
    private AutoService autoService;
    @Autowired
    PersonaRepository personaRepository;
    @GetMapping
    public ResponseEntity<?> getPersonas(){
        List<PersonaResponse> listaPersonaResponse=null;
        try{
            listaPersonaResponse=personaService.listPersonas();
        
        }catch(Exception e){
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(listaPersonaResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("persona not found").build());
        return ResponseEntity.ok(listaPersonaResponse);
    }
    @PostMapping
    public ResponseEntity<?> insertPersona(@RequestBody PersonaRequest personaRequest){
        logger.info(">insert"+personaRequest.toString());
        PersonaResponse personaResponse;
        try{
            personaResponse=personaService.insertPersona(personaRequest);
            
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(personaResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("persona not insert").build());
        return ResponseEntity.ok(personaResponse);     
    }
    @PutMapping()
    public ResponseEntity<?> updatePersona(@RequestBody PersonaRequest personaRequest){ 
        logger.info(">update" + personaRequest.toString());
        PersonaResponse personaResponse;
        try{
            personaResponse=personaService.findPersona(personaRequest.getIdPersona());
            if(personaResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("persona not found").build());
            personaResponse=personaService.updatePersona(personaRequest);
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(personaResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("Persona not update").build());
        return ResponseEntity.ok(personaResponse);
    }
    @DeleteMapping()
    public ResponseEntity<?> deletePersona(@RequestBody PersonaRequest personaRequest){
        logger.info(">delete" + personaRequest.toString());
        PersonaResponse personaResponse;
        try{
            personaResponse=personaService.findPersona(personaRequest.getIdPersona());
            if(personaResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("persona not found for delete").build());
            personaService.deletePersona(personaRequest.getIdPersona());
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }
        return ResponseEntity.ok(personaResponse);        
    }
    @GetMapping("/find")
    public ResponseEntity<?> findUsuarioById(@RequestBody PersonaRequest personaRequest){
      logger.info(">find" + personaRequest.toString());
        PersonaResponse personaResponse;
        try{
            personaResponse=personaService.findPersona(personaRequest.getIdPersona());
        }catch(Exception e){
            
            logger.error("error inesperado",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);     
        }        
        if(personaResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("persona not found").build());
        return ResponseEntity.ok(personaResponse);      
    } 
    
    @GetMapping("/buscar")
    public ResponseEntity<Persona> buscarPersona(@RequestParam String filtro) {
        System.out.println("Buscando persona con filtro: " + filtro);
        Optional<Persona> persona = personaRepository.findByNroDocumento(filtro)
                /*.or(() -> personaRepository.findByNombresContainingIgnoreCase(filtro))*/;
        System.out.println(persona.toString());
    return persona.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/autos/persona/{idPersona}")
    public List<AutoResponse> getAutosPorPersona(@PathVariable Integer idPersona) {
        return personaService.findByPersona(idPersona);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> getPersonaById(@PathVariable Integer id) {
        PersonaResponse persona = personaService.getPersonaById(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(persona);
    }
}