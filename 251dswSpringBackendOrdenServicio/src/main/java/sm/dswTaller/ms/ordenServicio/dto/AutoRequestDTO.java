package sm.dswTaller.ms.ordenServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldair
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoRequestDTO {
    private String placa;
    private String color;
    private Integer anio;

    private Integer idModelo;     // Relación con Modelo
    private Integer idPersona;    // Relación con Persona
}