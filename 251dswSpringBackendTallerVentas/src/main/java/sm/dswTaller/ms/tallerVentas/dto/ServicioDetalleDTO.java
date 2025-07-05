package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDetalleDTO {
    private Long idServicio;
    private String nombre;
    private BigDecimal precio;
} 