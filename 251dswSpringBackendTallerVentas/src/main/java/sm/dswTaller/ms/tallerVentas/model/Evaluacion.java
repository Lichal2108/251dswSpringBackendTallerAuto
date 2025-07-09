package sm.dswTaller.ms.tallerVentas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;
    
    @Column(name = "id_encuesta", nullable = false)
    @NotNull(message = "El ID de la encuesta es obligatorio")
    private Long idEncuesta;
    
    @Column(name = "id_pregunta", nullable = false)
    @NotNull(message = "El ID de la pregunta es obligatorio")
    private Long idPregunta;
    
    @Column(name = "puntaje_satisfaccion", nullable = false)
    @NotNull(message = "El puntaje de satisfacción es obligatorio")
    @Min(value = 1, message = "El puntaje mínimo es 1")
    @Max(value = 5, message = "El puntaje máximo es 5")
    private Integer puntajeSatisfaccion;
    
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
} 