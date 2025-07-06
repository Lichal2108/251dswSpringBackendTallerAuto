package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // permite realizar codigo limpio
@Entity//permite realizat las operaciones CRUD
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Integer id;
    private String pregunta;
}
