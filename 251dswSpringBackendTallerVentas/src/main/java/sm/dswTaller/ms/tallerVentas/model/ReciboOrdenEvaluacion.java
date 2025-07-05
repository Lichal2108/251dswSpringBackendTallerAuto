package sm.dswTaller.ms.tallerVentas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recibo_orden_evaluacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReciboOrdenEvaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_recibo", nullable = false)
    private Long idRecibo;
    
    @Column(name = "id_cotizacion", nullable = false)
    private Long idCotizacion;
    
    @Column(name = "id_evaluacion", nullable = false)
    private Long idEvaluacion;
} 