package sm.dswTaller.ms.tallerAutomotriz.service;

import sm.dswTaller.ms.tallerAutomotriz.dto.InventarioAutoRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.InventarioAutoResponseDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.InventarioRevisionDTO;
import sm.dswTaller.ms.tallerAutomotriz.model.InventarioAuto;
import sm.dswTaller.ms.tallerAutomotriz.model.ItemInventario;
import sm.dswTaller.ms.tallerAutomotriz.model.Ost;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.InventarioAutoRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ItemInventarioRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.OstRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioAutoService {

    @Autowired private InventarioAutoRepository inventarioAutoRepository;
    @Autowired private  OstRepository ostRepository;
    @Autowired private ItemInventarioRepository itemRepository;
    /*
    public void registrarInventario(int idOst, List<InventarioAutoRequestDTO> items) {
    Ost ost = ostRepository.findById(idOst)
    .orElseThrow(() -> new RuntimeException("OST no encontrada"));
    
    for (InventarioAutoRequestDTO dto : items) {
    InventarioAuto inventario = InventarioAuto.builder()
    .idOst(ostRepository.findById(dto.getIdOst()).orElseThrow())
    .idItem(itemRepository.findById(dto.getIdItem()).orElseThrow())
    .cantidad(dto.getCantidad())
    .estado(dto.getEstado())
    .build();
    
    inventarioAutoRepository.save(inventario);
    }
    }*/
    public void guardarInventario(InventarioRevisionDTO inventarioRevisionDTO) {
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

    public List<InventarioAutoResponseDTO> obtenerPorOst(int idOst) {
        List<InventarioAuto> inventario = inventarioAutoRepository.findByOst_IdOst(Long.valueOf(idOst));
        return InventarioAutoResponseDTO.fromEntities(inventario);
    }
}
