
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

/**
 *
 * @author Aldair
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstMsResponseDTO {
    private Long idOst;
    private LocalDate fecha;
    private LocalDate fechaRevision;
    private LocalTime hora;
    private String nivelGasolina;
    private Integer kilometraje;
    
    private String direccion; // opcional, si quieres mostrar algo

    private String estado;    // opcional, si quieres mostrar algo

    private Long idAuto;
    private Long idRecepcionista;
    private Long idSupervisor;

    public static OstMsResponseDTO fromEntity(Ost ost) {
        return OstMsResponseDTO.builder()
            .idOst(ost.getIdOst())
            .fecha(ost.getFecha())
            .fechaRevision(ost.getFechaRevision())
            .hora(ost.getHora())
            .nivelGasolina(ost.getNivelGasolina())
            .kilometraje(ost.getKilometraje())
            .direccion(ost.getDireccion().getDireccion())
            .estado(ost.getEstado().getEstado())
            .idAuto(ost.getAuto())
            .idRecepcionista(ost.getRecepcionista())
            .idSupervisor(ost.getSupervisor())
            .build();
    }
}
