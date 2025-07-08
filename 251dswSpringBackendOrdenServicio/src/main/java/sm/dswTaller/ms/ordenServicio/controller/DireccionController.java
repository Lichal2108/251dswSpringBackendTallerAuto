/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.ordenServicio.model.Direccion;
import sm.dswTaller.ms.ordenServicio.service.DireccionService;

@RestController
@RequestMapping(path = "api/v1/direccion")
public class DireccionController {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    DireccionService direccionService;
    
    @GetMapping
    public List<Direccion> getMarcas() {
        return direccionService.findAll();
    }
}