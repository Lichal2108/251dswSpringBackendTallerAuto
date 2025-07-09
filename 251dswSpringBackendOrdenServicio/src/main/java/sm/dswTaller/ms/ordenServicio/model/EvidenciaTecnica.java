/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldair
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evidencia_tecnica")
public class EvidenciaTecnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String url;
    private String descripcion;
    private LocalDateTime fecha = LocalDateTime.now();
        // Foreign keys (importantes para el join)
    @Column(name = "id_ost")
    private Long idOst;

    @Column(name = "id_tecnico")
    private Long idTecnico;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id_ost", referencedColumnName = "id_ost", insertable = false, updatable = false),
        @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico", insertable = false, updatable = false)
    })
    private OstTecnico ostTecnico;
}
