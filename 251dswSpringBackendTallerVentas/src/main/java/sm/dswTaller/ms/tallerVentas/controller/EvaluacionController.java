package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.EvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.service.EvaluacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v2/evaluacion")
public class EvaluacionController {
    
    @Autowired
    private EvaluacionService evaluacionService;
    
    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDTO>> getAllEvaluaciones() {
        List<EvaluacionResponseDTO> evaluaciones = evaluacionService.getAllEvaluaciones();
        return ResponseEntity.ok(evaluaciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDTO> getEvaluacionById(@PathVariable Long id) {
        Optional<EvaluacionResponseDTO> evaluacion = evaluacionService.getEvaluacionById(id);
        return evaluacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/encuesta/{idEncuesta}")
    public ResponseEntity<List<EvaluacionResponseDTO>> getEvaluacionesByEncuesta(@PathVariable Long idEncuesta) {
        List<EvaluacionResponseDTO> evaluaciones = evaluacionService.getEvaluacionesByIdEncuesta(idEncuesta);
        return ResponseEntity.ok(evaluaciones);
    }
    
    @PostMapping("/{idEncuesta}")
    public ResponseEntity<EvaluacionResponseDTO> createEvaluacion(@PathVariable Long idEncuesta, @RequestBody EvaluacionRequestDTO request) {
        EvaluacionResponseDTO createdEvaluacion = evaluacionService.createEvaluacion(request, idEncuesta);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluacion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDTO> updateEvaluacion(@PathVariable Long id, @RequestBody EvaluacionRequestDTO request) {
        Optional<EvaluacionResponseDTO> updatedEvaluacion = evaluacionService.updateEvaluacion(id, request);
        return updatedEvaluacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluacion(@PathVariable Long id) {
        boolean deleted = evaluacionService.deleteEvaluacion(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
