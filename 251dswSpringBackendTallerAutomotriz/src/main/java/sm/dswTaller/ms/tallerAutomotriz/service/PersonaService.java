
package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.dto.AutoResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.PersonaRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.PersonaResponse;
import sm.dswTaller.ms.tallerAutomotriz.model.Auto;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoDocumento;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.AutoRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TipoDocumentoRepository;


@Service
public class PersonaService{
    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    
    public List<PersonaResponse> listPersonas(){
        return PersonaResponse.fromEntities(personaRepository.findAll());
    }
    public PersonaResponse insertPersona(PersonaRequest personaRequest) {
        Integer idTipoDoc = personaRequest.getIdTipoDoc();
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDoc).get();
        if(tipoDocumento==null) return new PersonaResponse();
        
        
        Persona persona = new Persona(
                personaRequest.getIdPersona(),
                personaRequest.getNroDocumento(),
                tipoDocumento,
                personaRequest.getApellidoPaterno(),
                personaRequest.getApellidoMaterno(),
                personaRequest.getNombres(),
                personaRequest.getDireccion(),
                personaRequest.getSexo(),
                personaRequest.getTelefono()
                 
        );
        persona=personaRepository.save(persona);
        return PersonaResponse.fromEntity(persona);
    }
    public PersonaResponse updatePersona(PersonaRequest personaRequest) {
        Integer idTipoDoc = personaRequest.getIdTipoDoc();
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDoc).get();
        if(tipoDocumento==null) return new PersonaResponse();
        
        
        Persona persona = new Persona(
                personaRequest.getIdPersona(),
                personaRequest.getNroDocumento(),
                tipoDocumento,
                personaRequest.getApellidoPaterno(),
                personaRequest.getApellidoMaterno(),
                personaRequest.getNombres(),
                personaRequest.getDireccion(),
                personaRequest.getSexo(),
                personaRequest.getTelefono()
                 
        );
        persona=personaRepository.save(persona);
        return PersonaResponse.fromEntity(persona);        


    }
    public void deletePersona(int id) {
        if (!personaRepository.existsById(id)) {
            throw new RuntimeException("Persona no encontrada");
        }
        personaRepository.deleteById(id);
    }
    public PersonaResponse findPersona(Integer id){
        Optional<Persona> result=personaRepository.findById(id);
        if(!result.isPresent())
            return null;
        return PersonaResponse.fromEntity(result.get());
        
        
    }
    public PersonaResponse getPersonaById(Integer id){
        Optional<Persona> result = personaRepository.findById(id);
        if (!result.isPresent()) return null;
        return PersonaResponse.fromEntity(result.get());
    }
    
        public List<AutoResponse> findByPersona(Integer idPersona) {
        List<Auto> autos = autoRepository.findByPersona_IdPersona(idPersona);
        System.out.println(Arrays.toString(autos.toArray()));
        return autos.stream()
                 .map(auto -> AutoResponse.builder()
                        .idAuto(auto.getIdAuto())
                        .placa(auto.getPlaca())
                        .modelo(auto.getModelo())
                        .anio(auto.getAnio())
                        .color(auto.getColor())
                        .persona(auto.getPersona())
                        .build()
                )
                .collect(Collectors.toList());
    }
}