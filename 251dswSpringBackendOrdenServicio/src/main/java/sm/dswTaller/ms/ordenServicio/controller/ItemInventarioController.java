package sm.dswTaller.ms.ordenServicio.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.ordenServicio.dto.ItemInventarioDTO;
import sm.dswTaller.ms.ordenServicio.service.ItemInventarioService;

@RestController
@RequestMapping("/api/v1/item-inventario")
public class ItemInventarioController {
    
    @Autowired
    private ItemInventarioService itemService;

    @GetMapping
    public List<ItemInventarioDTO> listar() {
        return itemService.listarItems();
    }
}
