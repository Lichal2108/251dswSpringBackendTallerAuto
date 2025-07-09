/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import sm.dswTaller.ms.tallerAutomotriz.model.Ost;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionResponse {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private Ost ost;
    private String estado;
    private LocalDateTime fechaExpiracion;

    public static CotizacionResponse fromEntity(Cotizacion cotizacion) {
        return CotizacionResponse.builder()
                .id(cotizacion.getId())
                .fecha(cotizacion.getFecha())
                .total(cotizacion.getTotal())
                .ost(cotizacion.getOst()) // retorna el objeto completo
                .estado(cotizacion.getEstado().toString())
                .fechaExpiracion(cotizacion.getFechaExpiracion())
                .build();
    }
    public static List<CotizacionResponse> fromEntities(List<Cotizacion> cotizaciones) {
        return cotizaciones.stream()
                .map(CotizacionResponse::fromEntity)
                .collect(Collectors.toList());
    }    
    
}
