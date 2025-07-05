package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.PreguntasEvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.PreguntasEvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.service.PreguntasEvaluacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v2/preguntasevaluacion")
public class PreguntasEvaluacionController {
    
    @Autowired
    private PreguntasEvaluacionService preguntasEvaluacionService;
    
    @GetMapping("/obtener")
    public ResponseEntity<List<PreguntasEvaluacionResponseDTO>> getAllPreguntasEvaluacion() {
        List<PreguntasEvaluacionResponseDTO> preguntas = preguntasEvaluacionService.getAllPreguntasEvaluacion();
        return ResponseEntity.ok(preguntas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PreguntasEvaluacionResponseDTO> getPreguntaEvaluacionById(@PathVariable Long id) {
        Optional<PreguntasEvaluacionResponseDTO> pregunta = preguntasEvaluacionService.getPreguntaEvaluacionById(id);
        return pregunta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/subir")
    public ResponseEntity<PreguntasEvaluacionResponseDTO> createPreguntaEvaluacion(@RequestBody PreguntasEvaluacionRequestDTO request) {
        PreguntasEvaluacionResponseDTO createdPregunta = preguntasEvaluacionService.createPreguntaEvaluacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPregunta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PreguntasEvaluacionResponseDTO> updatePreguntaEvaluacion(@PathVariable Long id, @RequestBody PreguntasEvaluacionRequestDTO request) {
        Optional<PreguntasEvaluacionResponseDTO> updatedPregunta = preguntasEvaluacionService.updatePreguntaEvaluacion(id, request);
        return updatedPregunta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreguntaEvaluacion(@PathVariable Long id) {
        boolean deleted = preguntasEvaluacionService.deletePreguntaEvaluacion(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
