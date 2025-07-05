
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // permite realizar codigo limpio
@Entity//permite realizar las operaciones CRUD
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name="id_usuario")
    private Long id;
    @Column(name="nombre_usuario")
    private String nombreUsuario;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_rol", referencedColumnName = "id_rol")
    private Rol rol;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_persona", referencedColumnName = "id_persona")
    private Persona persona;
}
