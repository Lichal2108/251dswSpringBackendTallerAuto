/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

import org.springframework.stereotype.Service;

import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMaterialRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMultiplesMaterialesRequest;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMaterialResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMultiplesMaterialesResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialConCantidadResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.MaterialCotizacionRequest;
import sm.dswTaller.ms.tallerAutomotriz.model.*;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.*;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sm.dswTaller.ms.tallerAutomotriz.model.Cotizacion;
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionMaterial;
import sm.dswTaller.ms.tallerAutomotriz.model.Material;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.MaterialRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.CotizacionMaterialRepository;
import sm.dswTaller.ms.tallerAutomotriz.dto.CotizacionMaterialResponse;
import sm.dswTaller.ms.tallerAutomotriz.dto.AgregarMaterialRequest;
import sm.dswTaller.ms.tallerAutomotriz.model.CotizacionMaterialId;

/**
 *
 * @author Ciro
 */
@Service
public class CotizacionMaterialService {
    @Autowired
    private CotizacionRepository cotizacionRepo;

    @Autowired
    private MaterialRepository materialRepo;

    @Autowired
    private CotizacionMaterialRepository cotizacionMaterialRepo;

    private static final Logger logger = LoggerFactory.getLogger(CotizacionMaterialService.class);

    public CotizacionMaterialResponse agregarMaterialACotizacion(AgregarMaterialRequest request) {
        Cotizacion cotizacion = cotizacionRepo.findById(request.getIdCotizacion())
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        Material material = materialRepo.findById(request.getIdMaterial())
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        CotizacionMaterialId id = new CotizacionMaterialId(request.getIdCotizacion(), request.getIdMaterial());

        if (cotizacionMaterialRepo.existsById(id)) {
            throw new RuntimeException("El material ya está agregado a esta cotización.");
        }

        CotizacionMaterial cotizacionMaterial = CotizacionMaterial.builder()
                .id(id)
                .cotizacion(cotizacion)
                .material(material)
                .cantidad(request.getCantidad())
                .build();

        cotizacionMaterialRepo.save(cotizacionMaterial);

        return CotizacionMaterialResponse.fromEntity(material);
    }

    @Transactional
    public CotizacionMultiplesMaterialesResponse agregarMultiplesMaterialesACotizacion(
            AgregarMultiplesMaterialesRequest request) {

        Cotizacion cotizacion = cotizacionRepo.findById(request.getIdCotizacion())
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        List<CotizacionMaterialResponse> materialesAgregados = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        for (MaterialCotizacionRequest materialReq : request.getMateriales()) {
            try {
                Material material = materialRepo.findById(materialReq.getIdMaterial())
                        .orElseThrow(() -> new RuntimeException("Material no encontrado"));

                CotizacionMaterialId id = new CotizacionMaterialId(
                        request.getIdCotizacion(),
                        materialReq.getIdMaterial());

                if (!cotizacionMaterialRepo.existsById(id)) {
                    CotizacionMaterial cotizacionMaterial = CotizacionMaterial.builder()
                            .id(id)
                            .cotizacion(cotizacion)
                            .material(material)
                            .cantidad(materialReq.getCantidad())
                            .build();

                    cotizacionMaterialRepo.save(cotizacionMaterial);
                    materialesAgregados.add(CotizacionMaterialResponse.fromEntity(material));
                } else {
                    errores.add("Material " + materialReq.getIdMaterial() + " ya existe en cotización");
                }
            } catch (Exception e) {
                errores.add("Error con material " + materialReq.getIdMaterial() + ": " + e.getMessage());
            }
        }

        if (!errores.isEmpty()) {
            logger.warn("Errores al agregar materiales: {}", String.join(", ", errores));
        }

        return CotizacionMultiplesMaterialesResponse.builder()
                .idCotizacion(request.getIdCotizacion())
                .materialesAgregados(materialesAgregados)
                .build();
    }
    public List<MaterialConCantidadResponse> obtenerMaterialesDeCotizacion(Long idCotizacion) {
        List<CotizacionMaterial> materiales = cotizacionMaterialRepo.findByCotizacionId(idCotizacion);
        
        // Obtener el estado de la cotización
        Cotizacion cotizacion = cotizacionRepo.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        return materiales.stream()
                .map(cm -> MaterialConCantidadResponse.fromEntity(
                    cm.getMaterial(), 
                    cm.getCantidad(), 
                    cotizacion.getEstado()))
                .toList();
    }


    public Material guardarProducto(Material producto) {
        return materialRepo.save(producto);
    }

    
}
