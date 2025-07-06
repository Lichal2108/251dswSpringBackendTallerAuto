package sm.dswTaller.ms.tallerAutomotriz.model;

import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.utils.EstadoCotizacion;
import java.time.LocalDateTime;


@Data // permite realizar codigo limpio
@Entity//permite realizat las operaciones CRUD
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cotizacion")
    private Long id;

    @Column(name = "fecha_creacion")
    private LocalDateTime fecha;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_ost")
    private Ost ost;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoCotizacion estado = EstadoCotizacion.PENDIENTE;

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    /*@OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL)
    private List<CotizacionMaterial> materiales;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL)
    private List<CotizacionServicio> servicios;*/


    public boolean expirada() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

    /**
     * Establece la fecha de expiración basada en la fecha de creación
     * La expiración se establece a 5 días después de la creación
     */
    public void establecerFechaExpiracion() {
        if (fecha != null) {
            this.fechaExpiracion = fecha.plusDays(5);
        }
    }

    /**
     * Establece la fecha de expiración basada en la fecha actual
     * Se usa para extender el tiempo de expiración
     */
    public void establecerFechaExpiracionDesdeAhora() {
        this.fechaExpiracion = LocalDateTime.now().plusDays(5);
    }

}
