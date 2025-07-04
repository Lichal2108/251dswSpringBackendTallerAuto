package sm.dswTaller.ms.tallerAutomotriz.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTecnicoDTO {
    private Long idTecnico;
    private Integer idEstado;
    private String observaciones;
}
