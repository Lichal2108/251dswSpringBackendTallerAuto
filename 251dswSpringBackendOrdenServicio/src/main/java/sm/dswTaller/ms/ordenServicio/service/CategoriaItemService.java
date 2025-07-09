package sm.dswTaller.ms.ordenServicio.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.model.CategoriaItem;
import sm.dswTaller.ms.ordenServicio.repository.CategoriaItemRepository;

@Service
public class CategoriaItemService {
    @Autowired
    private CategoriaItemRepository categoriaItemRepository;

    public List<CategoriaItem> findAll() {
        return categoriaItemRepository.findAll();
    }

    public CategoriaItem save(CategoriaItem categoria) {
        return categoriaItemRepository.save(categoria);
    }
}
