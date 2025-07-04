/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.BitacoraProblemas;
import sm.dswTaller.ms.tallerAutomotriz.model.TipoSolucion;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BitacoraProblemasResponseDTO {
    private int idProblema;
    private String descripcionProblema;
    private String solucion;
    private TipoSolucion tipoSolucion;
    private String fechaRegistro;
    public static BitacoraProblemasResponseDTO fromEntity(BitacoraProblemas bitacora) {
        return BitacoraProblemasResponseDTO.builder()
                .idProblema(bitacora.getIdProblema())
                .descripcionProblema(bitacora.getDescripcionProblema())
                .solucion(bitacora.getSolucion())
                .tipoSolucion(bitacora.getTipoSolucion()) // objeto completo
                .fechaRegistro(bitacora.getFechaRegistro() != null ? bitacora.getFechaRegistro().toString() : null)
                .build();
    }

    public static List<BitacoraProblemasResponseDTO> fromEntities(List<BitacoraProblemas> bitacoras) {
        return bitacoras.stream()
                .map(BitacoraProblemasResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }    
    
    
}
