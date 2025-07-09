package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboResponseDTO;
import sm.dswTaller.ms.tallerVentas.dto.ReciboClienteDTO;
import sm.dswTaller.ms.tallerVentas.dto.DetalleReciboDTO;
import sm.dswTaller.ms.tallerVentas.dto.*;
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
    
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReciboClienteDTO>> obtenerRecibosPorCliente(@PathVariable Long idCliente) {
        try {
            List<ReciboClienteDTO> recibos = reciboService.obtenerRecibosPorCliente(idCliente);
            return ResponseEntity.ok(recibos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{idRecibo}/detalle")
    public ResponseEntity<DetalleReciboDTO> obtenerDetalleRecibo(@PathVariable Long idRecibo) {
        try {
            DetalleReciboDTO recibo = reciboService.obtenerDetalleRecibo(idRecibo);
            if (recibo != null) {
                return ResponseEntity.ok(recibo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{idRecibo}/descargar")
    public ResponseEntity<byte[]> descargarRecibo(@PathVariable Long idRecibo) {
        try {
            byte[] pdfBytes = reciboService.generarPDFRecibo(idRecibo);
            if (pdfBytes != null) {
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=recibo-" + idRecibo + ".pdf")
                    .header("Content-Type", "application/pdf")
                    .body(pdfBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
