package sm.dswTaller.ms.tallerVentas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preguntas_evaluacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntasEvaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "pregunta", nullable = false, columnDefinition = "TEXT")
    private String pregunta;
} 