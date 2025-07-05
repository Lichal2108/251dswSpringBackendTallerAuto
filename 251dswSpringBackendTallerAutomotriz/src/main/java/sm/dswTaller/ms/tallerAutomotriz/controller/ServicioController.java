/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.ServicioRepository;
import sm.dswTaller.ms.tallerAutomotriz.model.Servicio;
import sm.dswTaller.ms.tallerAutomotriz.dto.ServicioResponse;

@RestController
@RequestMapping("/api/v1/servicios")
public class ServicioController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<ServicioResponse> getServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        logger.info("Listando todos los servicios: {}", servicios.size());
        return ServicioResponse.fromEntities(servicios);
    }    
}
