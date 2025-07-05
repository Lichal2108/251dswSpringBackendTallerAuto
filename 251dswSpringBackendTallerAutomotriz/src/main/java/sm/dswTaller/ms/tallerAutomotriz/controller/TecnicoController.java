/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.service.TecnicoService;
import sm.dswTaller.ms.tallerAutomotriz.dto.TecnicoDTO;

@RestController
@RequestMapping("/api/v1/tecnico")
public class TecnicoController {
    
    @Autowired private TecnicoService tecnicoService;
    
    @GetMapping()
    public ResponseEntity<List<TecnicoDTO>> obtenerTodos() {
        List<TecnicoDTO> tecnicos = tecnicoService.obtenerTodos();
        return ResponseEntity.ok(tecnicos);
    }
}
