package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoDocumento;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponse {
    private int idPersona;
    private String nroDocumento;
    private TipoDocumento tipoDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String direccion;
    private String sexo;
    private String telefono;
    public static PersonaResponse fromEntity(Persona persona) {
        return PersonaResponse.builder()
                .idPersona(persona.getIdPersona())
                .nroDocumento(persona.getNroDocumento())
                .tipoDocumento(persona.getTipoDocumento())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .nombres(persona.getNombres())
                .direccion(persona.getDireccion())
                .sexo(persona.getSexo())
                .telefono(persona.getTelefono())
                .build();
    }
    public static List<PersonaResponse> fromEntities(List<Persona> personas) {
        return personas.stream()
                .map(PersonaResponse::fromEntity)
                .collect(Collectors.toList());
    }

   
}