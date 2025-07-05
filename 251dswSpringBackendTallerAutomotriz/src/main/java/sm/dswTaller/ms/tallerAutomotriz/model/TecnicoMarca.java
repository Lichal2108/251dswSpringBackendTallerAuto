
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tecnico_marca")
public class TecnicoMarca {

    @EmbeddedId
    private TecnicoMarcaId id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @MapsId("idMarca")
    @JoinColumn(name = "id_marca")
    private Marca marca;

}