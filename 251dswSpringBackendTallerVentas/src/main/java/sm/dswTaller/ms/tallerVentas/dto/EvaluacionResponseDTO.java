package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionResponseDTO {
    private Long idEvaluacion;
    private Long idEncuesta;
    private Long idPregunta;
    private String pregunta;
    private Integer puntajeSatisfaccion;
    private String comentario;
} 