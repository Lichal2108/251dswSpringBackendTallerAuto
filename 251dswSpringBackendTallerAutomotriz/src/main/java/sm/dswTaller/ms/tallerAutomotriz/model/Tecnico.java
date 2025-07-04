
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tecnico")
public class Tecnico {

    @Id
    @Column(name = "id_tecnico")
    private Long id; // Igual al ID de usuario

    private String especialidad;

    @Column(name = "experiencia_anios")
    private int experienciaAnios;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TecnicoMarca> marcas;
    
    @OneToMany(mappedBy = "tecnico") // relación con ost_tecnico
    private List<OstTecnico> ostsAsignadas;
    
    
    @OneToOne
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_usuario")
    private Usuario usuario;
}