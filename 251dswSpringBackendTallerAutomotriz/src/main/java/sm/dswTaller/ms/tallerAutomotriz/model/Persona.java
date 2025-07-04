

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
@Entity//permite realizat las operaciones CRUD
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="persona")
public class Persona {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name="id_persona")
    private int idPersona;
    @Column(name="nro_documento")    
    private String nroDocumento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipo_doc", referencedColumnName = "id_tipo_doc")
    private TipoDocumento tipoDocumento;
    
    @Column(name="apellido_paterno")
    private String apellidoPaterno;
    
     @Column(name="apellido_materno")
    private String apellidoMaterno;
     
    private String nombres;
    
    private String direccion;
    
    private String sexo;
    private String telefono;
    
}
