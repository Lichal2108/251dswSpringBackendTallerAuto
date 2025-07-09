/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.MaterialRepository;
import sm.dswTaller.ms.tallerAutomotriz.model.Material;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/materiales")
public class MaterialController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping
    public List<MaterialResponse> getMateriales() {
        List<Material> materiales = materialRepository.findAll();
        logger.info("Listando todos los materiales: {}", materiales.size());
        return MaterialResponse.fromEntities(materiales);
    }

    // Obtener material por ID
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getMaterialById(@PathVariable Long id) {
        Optional<Material> materialOpt = materialRepository.findById(id);
        if (materialOpt.isPresent()) {
            return ResponseEntity.ok(MaterialResponse.fromEntity(materialOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear material
    @PostMapping
    public ResponseEntity<MaterialResponse> createMaterial(@RequestBody Material material) {
        Material saved = materialRepository.save(material);
        return ResponseEntity.ok(MaterialResponse.fromEntity(saved));
    }

    // Editar material
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> updateMaterial(@PathVariable Long id, @RequestBody Material material) {
        Optional<Material> materialOpt = materialRepository.findById(id);
        if (materialOpt.isPresent()) {
            Material existing = materialOpt.get();
            existing.setNombre(material.getNombre());
            existing.setStock(material.getStock());
            existing.setPrecio(material.getPrecio());
            Material updated = materialRepository.save(existing);
            return ResponseEntity.ok(MaterialResponse.fromEntity(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar material
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar solo el stock de un material
    @PatchMapping("/{id}/stock")
    public ResponseEntity<MaterialResponse> updateStock(@PathVariable Long id, @RequestBody Integer nuevoStock) {
        Optional<Material> materialOpt = materialRepository.findById(id);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setStock(nuevoStock);
            Material updated = materialRepository.save(material);
            return ResponseEntity.ok(MaterialResponse.fromEntity(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
