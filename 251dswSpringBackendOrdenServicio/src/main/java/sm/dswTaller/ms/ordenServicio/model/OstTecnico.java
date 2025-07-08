package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ost_tecnico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstTecnico {
    
    @EmbeddedId
    private OstTecnicoId id;//

    @ManyToOne
    @JoinColumn(name = "id_ost", insertable = false, updatable = false)
    private Ost ordenServicio;


    @ManyToOne
    @JoinColumn(name = "id_estado")
    private TipoEstado estado;
    
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    private String observaciones;
}