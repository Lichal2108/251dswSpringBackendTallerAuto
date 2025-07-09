
package sm.dswTaller.ms.ordenServicio.model;

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
public class OstTecnicoId implements Serializable {
    @Column(name = "id_ost") 
    private Long idOst;
    
    @Column(name = "id_tecnico") 
    private Long idTecnico;
    
        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OstTecnicoId)) return false;
        OstTecnicoId that = (OstTecnicoId) o;
        return Objects.equals(idTecnico, that.idTecnico) &&
               Objects.equals(idOst, that.idOst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOst, idTecnico);
    }
}