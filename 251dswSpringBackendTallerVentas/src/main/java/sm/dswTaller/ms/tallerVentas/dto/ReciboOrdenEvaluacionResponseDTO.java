package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReciboOrdenEvaluacionResponseDTO {
    private Long id;
    private Long idRecibo;
    private Long idCotizacion;
    private Long idEvaluacion;
} 