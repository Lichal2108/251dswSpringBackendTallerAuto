/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

import sm.dswTaller.ms.tallerAutomotriz.dto.ItemInventarioDTO;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ItemInventarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
