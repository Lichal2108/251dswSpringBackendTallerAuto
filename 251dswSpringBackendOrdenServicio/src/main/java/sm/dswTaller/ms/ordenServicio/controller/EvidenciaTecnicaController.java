/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sm.dswTaller.ms.ordenServicio.dto.EvidenciaRequestDTO;
import sm.dswTaller.ms.ordenServicio.service.EvidenciaTecnicaService;

/**
 *
 * @author Aldair
 */
@RestController
@RequestMapping("/api/v1/evidencias")
public class EvidenciaTecnicaController {

    @Autowired private EvidenciaTecnicaService evidenciaService;

@PostMapping("/subir")
public ResponseEntity<?> subirEvidencia(
    @RequestParam("archivo") MultipartFile archivo,
    @RequestParam("idOst") Long idOst,
    @RequestParam("idTecnico") Long idTecnico,
    @RequestParam("descripcion") String descripcion
) {
    try {
        EvidenciaRequestDTO dto = new EvidenciaRequestDTO();
        dto.setIdOst(idOst);
        dto.setIdTecnico(idTecnico);
        dto.setDescripcion(descripcion);

        evidenciaService.guardarEvidencia(archivo, dto);
        return ResponseEntity.ok("Evidencia subida correctamente");
    } catch (Exception e) {
        e.printStackTrace(); // 👈 importante para ver el error en consola
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar evidencia: " + e.getMessage());
    }
}

}
