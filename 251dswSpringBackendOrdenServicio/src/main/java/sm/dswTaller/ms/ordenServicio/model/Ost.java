
package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // permite realizar codigo limpio
@Entity//permite realizat las operaciones CRUD
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orden_servicio_tecnico")
public class Ost {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name="id_ost")
    private Long idOst;
    
    private LocalDate fecha;
    
    @Column(name="fecha_revision")
    private LocalDate fechaRevision;
    
    private LocalTime hora;
    
    @Column(name="nivel_gasolina")
    private String nivelGasolina;
    
    private Integer kilometraje; 
    
    
    @ManyToOne
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    private TipoEstado estado;
    
    @Column(name = "id_auto")
    private Long auto;
    
    @Column(name = "id_recepcionista")
    private Long recepcionista;
    
    @Column(name = "id_supervisor")
    private Long supervisor;
    
}
