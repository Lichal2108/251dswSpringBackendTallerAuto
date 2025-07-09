/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldair
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaTecnicaDTO {
    private Long id;
    private String nombreArchivo;
    private String url;
    private String descripcion;
    private LocalDateTime fecha;
}