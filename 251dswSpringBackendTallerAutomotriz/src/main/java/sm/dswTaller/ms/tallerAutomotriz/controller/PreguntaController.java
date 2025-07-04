/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PreguntaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.OrdenPreguntaRepository;
import sm.dswTaller.ms.tallerAutomotriz.model.Pregunta;

@RestController
@RequestMapping("api/v1/pregunta")
public class PreguntaController {
    
    @Autowired private PreguntaRepository preguntaRepo;
    @Autowired private OrdenPreguntaRepository ordenPreguntaRepository;
    
    @GetMapping
    public List<Pregunta> getPreguntas() {
        return preguntaRepo.findAll();
    }
    
    @GetMapping("/por-ost/{idOst}")
        public ResponseEntity<List<String>> getPreguntasPorOst(@PathVariable Integer idOst) {
        List<String> preguntas = ordenPreguntaRepository.findByOstIdOst(idOst).stream()
            .map(op -> op.getPregunta().getPregunta())
            .collect(Collectors.toList());

        return ResponseEntity.ok(preguntas);
    }
}
