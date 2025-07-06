package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaSatisfaccionResponseDTO {
    private Long idReciboOrdenEvaluacion;
    private Long idRecibo;
    private Long idCotizacion;
    private List<EvaluacionResponseDTO> evaluaciones;
    private BigDecimal promedioSatisfaccion;
    private String mensaje;
} 