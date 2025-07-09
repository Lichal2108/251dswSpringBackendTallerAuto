package sm.dswTaller.ms.ordenServicio.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.ordenServicio.model.Ost;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstResponseDTO {
    private Long idOst;
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
    private Long idPersona;
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
    
    public static OstResponseDTO fromEntity(
            Ost ost,
            AutoDTO auto,
            PersonaDTO persona,
            UsuarioDTO recep,
            UsuarioDTO superv) {

        return OstResponseDTO.builder()
            .idOst(ost.getIdOst())
            .fecha(ost.getFecha())
            .fechaRevision(ost.getFechaRevision())
            .hora(ost.getHora())
            .nivelGasolina(ost.getNivelGasolina())
            .kilometraje(ost.getKilometraje())
            .direccion(ost.getDireccion().getDireccion())
            .estado(ost.getEstado().getEstado())

            // Auto
            .placa(auto.getPlaca())
            .modelo(auto.getModelo())
            .color(auto.getColor())
            .anio(auto.getAnio())

            // Persona asociada al auto
            .idPersona(persona.getIdPersona())
            .nombres(persona.getNombres())
            .apellidoPaterno(persona.getApellidoPaterno())
            .apellidoMaterno(persona.getApellidoMaterno())
            .nroDocumento(persona.getNroDocumento())
            .tipoDocumento(persona.getTipoDocumento().getTipoDoc())
            .telefono(persona.getTelefono())

            // Usuarios (Recepcionista y Supervisor)
            .recepcionista(recep != null && recep.getPersona() != null
                ? recep.getPersona().getNombres() + " " +
                  recep.getPersona().getApellidoPaterno() + " " +
                  recep.getPersona().getApellidoMaterno()
                : null)

            .supervisor(superv != null && superv.getPersona() != null
                ? superv.getPersona().getNombres() + " " +
                  superv.getPersona().getApellidoPaterno() + " " +
                  superv.getPersona().getApellidoMaterno()
                : null)

            .build();
    }
}
