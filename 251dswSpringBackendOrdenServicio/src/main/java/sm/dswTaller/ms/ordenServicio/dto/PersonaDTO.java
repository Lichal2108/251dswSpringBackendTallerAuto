/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private Long idPersona;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nroDocumento;
    private TipoDocumentoDTO tipoDocumento;
    private String telefono;
}