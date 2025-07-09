
package sm.dswTaller.ms.ordenServicio.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.dto.ItemInventarioDTO;
import sm.dswTaller.ms.ordenServicio.repository.ItemInventarioRepository;

@Service
public class ItemInventarioService {
        @Autowired
    private ItemInventarioRepository itemRepo;

    public List<ItemInventarioDTO> listarItems() {
        return itemRepo.findAll().stream()
                .map(ItemInventarioDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
