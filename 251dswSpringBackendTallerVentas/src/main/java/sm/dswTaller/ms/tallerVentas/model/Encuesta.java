package sm.dswTaller.ms.tallerVentas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "encuesta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encuesta")
    private Long idEncuesta;
    
    @Column(name = "id_recibo", nullable = false)
    private Long idRecibo;
    
    @Column(name = "id_cotizacion", nullable = false)
    private Long idCotizacion;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "promedio_satisfaccion", columnDefinition = "DOUBLE PRECISION")
    private Double promedioSatisfaccion;
    
    @Column(name = "comentario_general", columnDefinition = "TEXT")
    private String comentarioGeneral;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
} 