
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data  
@NoArgsConstructor
@AllArgsConstructor
public class OrdenPreguntaPK implements Serializable {
    
    @Column(name = "id_ost")       // Mapea al nombre exacto de la columna
    private Integer idOst;

    @Column(name = "id_pregunta")  // Mapea al nombre exacto de la columna
    private Integer idPregunta;
    
        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdenPreguntaPK)) return false;
        OrdenPreguntaPK that = (OrdenPreguntaPK) o;
        return Objects.equals(idOst, that.idOst) &&
               Objects.equals(idPregunta, that.idPregunta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOst, idPregunta);
    }
}