package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerVentas.dto.EncuestaSatisfaccionRequestDTO;
import sm.dswTaller.ms.tallerVentas.dto.EncuestaSatisfaccionResponseDTO;
import sm.dswTaller.ms.tallerVentas.service.EncuestaSatisfaccionService;

@RestController
@RequestMapping(path = "api/v2/encuesta-satisfaccion")
public class EncuestaSatisfaccionController {
    
    @Autowired
    private EncuestaSatisfaccionService encuestaSatisfaccionService;
    
    @PostMapping
    public ResponseEntity<EncuestaSatisfaccionResponseDTO> procesarEncuestaSatisfaccion(@RequestBody EncuestaSatisfaccionRequestDTO request) {
        try {
            EncuestaSatisfaccionResponseDTO response = encuestaSatisfaccionService.procesarEncuestaSatisfaccion(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/recibo/{idRecibo}")
    public ResponseEntity<EncuestaSatisfaccionResponseDTO> obtenerEncuestaPorRecibo(@PathVariable Long idRecibo) {
        try {
            EncuestaSatisfaccionResponseDTO response = encuestaSatisfaccionService.obtenerEncuestaPorRecibo(idRecibo);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 