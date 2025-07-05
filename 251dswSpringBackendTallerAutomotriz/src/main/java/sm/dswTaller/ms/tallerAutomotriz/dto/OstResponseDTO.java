package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Auto;
import sm.dswTaller.ms.tallerAutomotriz.model.Ost;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoEstado;
import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.OrdenPreguntaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.OstRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ciro
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstResponseDTO {
    private Integer idOst;
    private LocalDate fecha;
    private LocalDate fechaRevision;
    private LocalTime hora;

    private String nivelGasolina;
    private Integer kilometraje;
    
    private String direccion;

    private String estado;

    // Auto
    private String placa;
    private String modelo;
    private String marca;
    private String color;
    private Integer anio;

    // Persona asociada al auto
    private Integer idPersona;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nroDocumento;
    private String tipoDocumento;
    private String telefono;

    // Recepcionista
    private String recepcionista;
    private String supervisor;
    
    private List<String> preguntasOst;
    
    public static OstResponseDTO fromEntity(Ost ost) {
        Auto auto = ost.getAuto();
        Persona persona = (auto != null) ? auto.getPersona() : null;
        return OstResponseDTO.builder()
            .idOst(ost.getIdOst())
            .fecha(ost.getFecha())
            .fechaRevision(ost.getFechaRevision())
            .hora(ost.getHora())
            .nivelGasolina(ost.getNivelGasolina())
            .kilometraje(ost.getKilometraje())
            .direccion(ost.getDireccion().getDireccion())
            .estado(ost.getEstado() != null ? ost.getEstado().getEstado() : null)

            // Auto
            .placa(auto != null ? auto.getPlaca() : null)
            .modelo(auto != null && auto.getModelo() != null ? auto.getModelo().getNombre() : null)
            .marca(auto != null && auto.getModelo() != null && auto.getModelo().getMarca() != null
                    ? auto.getModelo().getMarca().getNombre() : null)
            .color(auto != null ? auto.getColor() : null)
            .anio(auto != null ? auto.getAnio() : null)

            // Persona (propietaria del auto)
            .idPersona(persona != null ? persona.getIdPersona() : null)
            .nombres(persona != null ? persona.getNombres() : null)
            .apellidoPaterno(persona != null ? persona.getApellidoPaterno() : null)
            .apellidoMaterno(persona != null ? persona.getApellidoMaterno() : null)
            .nroDocumento(persona != null ? persona.getNroDocumento() : null)
            .tipoDocumento(persona != null && persona.getTipoDocumento() != null
                    ? persona.getTipoDocumento().getTipoDoc(): null)
            .telefono(persona != null ? persona.getTelefono() : null)

            // Recepcionista
            .recepcionista(ost.getRecepcionista() != null
                    ? ost.getRecepcionista().getPersona().getNombres()+" "+
                    ost.getRecepcionista().getPersona().getApellidoPaterno()+" "+
                        ost.getRecepcionista().getPersona().getApellidoMaterno(): null)
                
            // Recepcionista
            .supervisor(ost.getSupervisor()!= null
                    ? ost.getSupervisor().getPersona().getNombres()+" "+
                    ost.getSupervisor().getPersona().getApellidoPaterno()+" "+
                        ost.getSupervisor().getPersona().getApellidoMaterno(): null)
            .build();
    }

    public static List<OstResponseDTO> fromEntities(List<Ost> osts) {
         return osts.stream()
                 .map(OstResponseDTO::fromEntity)
                 .collect(Collectors.toList());
         
     }    
   

}