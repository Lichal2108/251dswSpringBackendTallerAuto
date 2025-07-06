package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboResponseDTO;
import sm.dswTaller.ms.tallerVentas.service.ReciboService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v2/recibo")
public class ReciboController {
    
    @Autowired
    private ReciboService reciboService;
    
    @GetMapping
    public ResponseEntity<List<ReciboResponseDTO>> getAllRecibos() {
        List<ReciboResponseDTO> recibos = reciboService.getAllRecibos();
        return ResponseEntity.ok(recibos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReciboResponseDTO> getReciboById(@PathVariable Long id) {
        Optional<ReciboResponseDTO> recibo = reciboService.getReciboById(id);
        return recibo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ReciboResponseDTO> createRecibo(@RequestBody ReciboRequestDTO request) {
        ReciboResponseDTO createdRecibo = reciboService.createRecibo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecibo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReciboResponseDTO> updateRecibo(@PathVariable Long id, @RequestBody ReciboRequestDTO request) {
        Optional<ReciboResponseDTO> updatedRecibo = reciboService.updateRecibo(id, request);
        return updatedRecibo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecibo(@PathVariable Long id) {
        boolean deleted = reciboService.deleteRecibo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/marcar-para-evaluacion")
    public ResponseEntity<Void> marcarReciboParaEvaluacion(@PathVariable Long id) {
        boolean marcado = reciboService.marcarReciboParaEvaluacion(id);
        if (marcado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/listo-para-evaluacion")
    public ResponseEntity<Boolean> verificarReciboListoParaEvaluacion(@PathVariable Long id) {
        boolean listo = reciboService.verificarReciboListoParaEvaluacion(id);
        return ResponseEntity.ok(listo);
    }
}
