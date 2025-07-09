package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "inventario_auto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "id_ost", nullable = false)
    private Ost ost;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private ItemInventario item;

    private Integer cantidad;

    private String estado;
}
