package sm.dswTaller.ms.tallerVentas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionRequestDTO {
    @NotNull(message = "El ID de la pregunta es obligatorio")
    private Long idPregunta;
    
    @NotNull(message = "El puntaje de satisfacción es obligatorio")
    @Min(value = 1, message = "El puntaje mínimo es 1")
    @Max(value = 5, message = "El puntaje máximo es 5")
    private Integer puntajeSatisfaccion;
    
    private String comentario;
} 