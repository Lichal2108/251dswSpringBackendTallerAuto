package sm.dswTaller.ms.tallerAutomotriz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReciboResponseDTO {
    private Long idRecibo;
    private Long idCliente;
    private Long idTecnico;
    private BigDecimal montoTotal;
    private LocalDate fecha;
} 