package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionPendienteEncuestaDTO {
    private Long idRecibo;
    private Long idCotizacion;
    private LocalDateTime fechaPago;
    private String descripcion;
    private BigDecimal monto;
    private Long idCliente;
    private String nombreCliente;
    private String placaVehiculo;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String estadoCotizacion;
    private LocalDateTime fechaVencimiento;
} 