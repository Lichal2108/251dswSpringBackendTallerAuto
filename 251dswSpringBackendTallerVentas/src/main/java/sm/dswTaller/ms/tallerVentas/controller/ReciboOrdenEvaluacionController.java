package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.ReciboOrdenEvaluacionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboOrdenEvaluacionResponseDTO;
import sm.dswTaller.ms.tallerVentas.service.ReciboOrdenEvaluacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v2/recibo-orden-evaluacion")
public class ReciboOrdenEvaluacionController {
    
    @Autowired
    private ReciboOrdenEvaluacionService reciboOrdenEvaluacionService;
    
    @GetMapping
    public ResponseEntity<List<ReciboOrdenEvaluacionResponseDTO>> getAllReciboOrdenEvaluacion() {
        List<ReciboOrdenEvaluacionResponseDTO> reciboOrdenEvaluacion = reciboOrdenEvaluacionService.getAllReciboOrdenEvaluacion();
        return ResponseEntity.ok(reciboOrdenEvaluacion);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReciboOrdenEvaluacionResponseDTO> getReciboOrdenEvaluacionById(@PathVariable Long id) {
        Optional<ReciboOrdenEvaluacionResponseDTO> reciboOrdenEvaluacion = reciboOrdenEvaluacionService.getReciboOrdenEvaluacionById(id);
        return reciboOrdenEvaluacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReciboOrdenEvaluacion(@PathVariable Long id) {
        boolean deleted = reciboOrdenEvaluacionService.deleteReciboOrdenEvaluacion(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
