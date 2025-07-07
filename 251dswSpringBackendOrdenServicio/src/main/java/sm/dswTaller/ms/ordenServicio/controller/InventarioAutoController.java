package sm.dswTaller.ms.ordenServicio.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.ordenServicio.dto.InventarioAutoResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.InventarioByOstDTO;
import sm.dswTaller.ms.ordenServicio.service.InventarioAutoService;

@RestController
@RequestMapping("/api/v1/inventario-auto")
public class InventarioAutoController {

    @Autowired private InventarioAutoService inventarioService;
    
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @PostMapping
    public ResponseEntity<?> guardarInventario(@RequestBody InventarioByOstDTO inventario) {
        inventarioService.guardarInventario(inventario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/por-ost/{idOst}")
    public ResponseEntity<List<InventarioAutoResponseDTO>> obtenerPorOst(@PathVariable Long idOst) {
        return ResponseEntity.ok(inventarioService.obtenerPorOst(idOst));
    }
}