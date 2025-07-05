/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.service.TecnicoMarcaService;
import sm.dswTaller.ms.tallerAutomotriz.dto.TecnicoMarcaRequestDTO;

@RestController
@RequestMapping("/api/v1/tecnico-marca")
@RequiredArgsConstructor
public class TecnicoMarcaController {

    private final TecnicoMarcaService tecnicoMarcaService;

    @PostMapping
    public ResponseEntity<String> asignarMarca(@RequestBody TecnicoMarcaRequestDTO dto) {
        tecnicoMarcaService.asignarMarcaATecnico(dto);
        return ResponseEntity.ok("Marca asignada correctamente al técnico.");
    }

    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<String>> obtenerMarcasPorTecnico(@PathVariable Long idTecnico) {
        List<String> marcas = tecnicoMarcaService.obtenerMarcasPorTecnico(idTecnico);
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<List<Long>> obtenerTecnicosPorMarca(@PathVariable Integer idMarca) {
        List<Long> tecnicos = tecnicoMarcaService.obtenerTecnicosPorMarca(idMarca);
        return ResponseEntity.ok(tecnicos);
    }
    
    @DeleteMapping
    public ResponseEntity<String> eliminarRelacion(@RequestBody TecnicoMarcaRequestDTO dto) {
        tecnicoMarcaService.eliminarRelacion(dto);
        return ResponseEntity.ok("Relación eliminada correctamente.");
    }
}