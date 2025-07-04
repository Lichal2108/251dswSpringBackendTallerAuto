/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

import sm.dswTaller.ms.tallerAutomotriz.model.CategoriaItem;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CategoriaItemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
