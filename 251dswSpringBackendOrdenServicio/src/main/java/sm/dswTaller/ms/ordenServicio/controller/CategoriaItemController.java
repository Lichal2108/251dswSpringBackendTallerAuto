package sm.dswTaller.ms.ordenServicio.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.ordenServicio.model.CategoriaItem;
import sm.dswTaller.ms.ordenServicio.service.CategoriaItemService;
 

@RestController
@RequestMapping("/api/v1/categoria-item")
public class CategoriaItemController {
    @Autowired private CategoriaItemService categoriaItemService;
    
    @GetMapping
    public List<CategoriaItem> getCategorias() {
        return categoriaItemService.findAll();
    }
}
