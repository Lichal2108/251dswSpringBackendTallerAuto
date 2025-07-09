package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orden_pregunta")
public class OrdenPregunta {

    @EmbeddedId
    private OrdenPreguntaPK id;
    
    
    @ManyToOne
    @MapsId("idOst")
    @JoinColumn(name = "id_ost")
    private Ost ost;

    @ManyToOne
    @MapsId("idPregunta")
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;
}