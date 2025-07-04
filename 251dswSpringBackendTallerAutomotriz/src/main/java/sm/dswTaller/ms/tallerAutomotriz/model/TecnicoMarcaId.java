
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoMarcaId implements Serializable {

    @Column(name = "id_tecnico")
    private Long id;

    @Column(name = "id_marca")
    private Integer idMarca;

    // equals y hashCode obligatorios

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TecnicoMarcaId)) return false;
        TecnicoMarcaId that = (TecnicoMarcaId) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(idMarca, that.idMarca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMarca);
    }
}