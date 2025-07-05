/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.dto.TecnicoDTO;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;
import sm.dswTaller.ms.tallerAutomotriz.model.Tecnico;
import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.PersonaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TecnicoMarcaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TecnicoRepository;

@Service
public class TecnicoService {
    
    @Autowired
    TecnicoRepository tecnicoRepository;
    
    @Autowired
    PersonaRepository personaRepository;
    
    @Autowired
    private TecnicoMarcaRepository tecnicoMarcaRepository;
    
    public List<TecnicoDTO> obtenerTodos() {
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        
        List<TecnicoDTO> listaTecnicos = new ArrayList<>();//findByTecnico_Id

        for (Tecnico t : tecnicos) {
            Usuario usuario = t.getUsuario();
            Optional<Persona> persona = personaRepository.findById(usuario.getPersona().getIdPersona());
            List<String> marcas = tecnicoMarcaRepository.findByTecnico_Id(t.getId())
                .stream()
                .map(tm -> tm.getMarca().getNombre())
                .collect(Collectors.toList());
            TecnicoDTO tecnicoDto = TecnicoDTO.builder()
                    .idTecnico(t.getId())
                    .nombre(persona.get().getNombres()+ " " +persona.get().getApellidoPaterno() + " " + persona.get().getApellidoMaterno())
                    .nro(persona.get().getNroDocumento())
                    .telefono(persona.get().getTelefono())
                    .especialidad(t.getEspecialidad())
                    .experienciaAnios(t.getExperienciaAnios())
                    .marcas(marcas)
                    .build();
            listaTecnicos.add(tecnicoDto);
        }
        return listaTecnicos;
    }
}
