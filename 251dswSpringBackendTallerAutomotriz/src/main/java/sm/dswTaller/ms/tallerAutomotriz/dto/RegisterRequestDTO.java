/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;
    private LocalDate fechaNacimiento; // why we use local date instead of date ?
    // we use LocalDate instead of Date because Date is deprecated in Java 8
    private String sexo;
    private String nroDocumento;
    
    private String tipoDocumento;
    private String telefono;
    private String nombreUsuario;
    private String password;
}
