package sm.dswTaller.ms.ordenServicio.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.dto.InventarioAutoRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.InventarioAutoResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.InventarioByOstDTO;
import sm.dswTaller.ms.ordenServicio.model.InventarioAuto;
import sm.dswTaller.ms.ordenServicio.model.ItemInventario;
import sm.dswTaller.ms.ordenServicio.model.Ost;
import sm.dswTaller.ms.ordenServicio.repository.InventarioAutoRepository;
import sm.dswTaller.ms.ordenServicio.repository.ItemInventarioRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstRepository;

@Service
public class InventarioAutoService {

    @Autowired private InventarioAutoRepository inventarioAutoRepository;
    @Autowired private  OstRepository ostRepository;
    @Autowired private ItemInventarioRepository itemRepository;

    public void guardarInventario(InventarioByOstDTO inventarioRevisionDTO) {
        Ost ost = ostRepository.findById(Long.valueOf(inventarioRevisionDTO.getIdOst()))
                    .orElseThrow(() -> new RuntimeException("OST no encontrada"));
        for (InventarioAutoRequestDTO dto : inventarioRevisionDTO.getInventario()) {
            
            ItemInventario item = itemRepository.findById(Long.valueOf(dto.getIdItem()))
                    .orElseThrow(() -> new RuntimeException("Item no encontrado"));
            InventarioAuto inventario = InventarioAuto.builder()
                .ost(ost)
                .item(item)
                .cantidad(dto.getCantidad())
                .estado(dto.getEstado())
                .build();
            
            inventarioAutoRepository.save(inventario);
        }
        ost.setKilometraje(inventarioRevisionDTO.getKilometraje());
        ost.setNivelGasolina(inventarioRevisionDTO.getNivelGasolina());
        ostRepository.save(ost);
    }

    public List<InventarioAutoResponseDTO> obtenerPorOst(Long idOst) {
        List<InventarioAuto> inventario = inventarioAutoRepository.findByOst_IdOst(idOst);
        return InventarioAutoResponseDTO.fromEntities(inventario);
    }
}
