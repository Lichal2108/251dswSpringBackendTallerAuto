package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaSatisfaccionRequestDTO {
    private Long idRecibo;
    private Long idCotizacion;
    private List<EvaluacionRequestDTO> evaluaciones;
} 