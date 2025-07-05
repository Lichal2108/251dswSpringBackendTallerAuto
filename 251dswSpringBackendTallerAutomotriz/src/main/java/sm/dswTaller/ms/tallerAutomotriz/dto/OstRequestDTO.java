
package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OstRequestDTO{
    private Integer idOst;
    private LocalDate fecha;
    private LocalDate fechaRevision;
    private LocalTime hora;
    private Integer idDireccion;
    
    private String nivelGasolina;
    private Integer kilometraje;
    
    private Integer idAuto;
    private Integer anio;
    private String placa;
    private Integer idModelo;
    private Integer idPersona;
    private String color;
    
    private Integer idEstado;
    private Integer IdRecepcionista;
    private Integer IdSupervisor;
    private List<Integer> preguntas;
    //private List<InventarioAutoRequestDTO> inventario;
}
