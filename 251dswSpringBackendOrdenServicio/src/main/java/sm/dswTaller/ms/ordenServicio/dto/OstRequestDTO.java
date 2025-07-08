package sm.dswTaller.ms.ordenServicio.dto;

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
    private Long idOst;
    private LocalDate fecha;
    private LocalDate fechaRevision;
    private LocalTime hora;
    private Integer idDireccion;
    
    private String nivelGasolina;
    private Integer kilometraje;
    
    private Long idAuto;
    private Long anio;
    private String placa;
    private Long idModelo;
    private Long idPersona;
    private String color;
    
    private Integer idEstado;
    private Long IdRecepcionista;
    private Long IdSupervisor;
    private List<Integer> preguntas;
    //private List<InventarioAutoRequestDTO> inventario;
}
