
package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.dto.TecnicoMarcaRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.model.Marca;
import sm.dswTaller.ms.tallerAutomotriz.model.Tecnico;
import sm.dswTaller.ms.tallerAutomotriz.model.TecnicoMarca;
import sm.dswTaller.ms.tallerAutomotriz.model.TecnicoMarcaId;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.MarcaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TecnicoMarcaRepository;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TecnicoRepository;

@Service
public class TecnicoMarcaService {

    @Autowired private TecnicoRepository tecnicoRepository;
    @Autowired private MarcaRepository marcaRepository;
    @Autowired private TecnicoMarcaRepository tecnicoMarcaRepository;

    public void asignarMarcaATecnico(TecnicoMarcaRequestDTO dto) {
        TecnicoMarcaId id = TecnicoMarcaId.builder()
        .id(dto.getIdTecnico())
        .idMarca(dto.getIdMarca())
        .build();

        if (tecnicoMarcaRepository.existsById(id)) {
            throw new RuntimeException("La relación ya existe.");
        }
        
        Tecnico tecnico = tecnicoRepository.findById(dto.getIdTecnico())
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        Marca marca = marcaRepository.findById(dto.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        TecnicoMarca tecnicoMarca = TecnicoMarca.builder()
                .id(id)
                .tecnico(tecnico)
                .marca(marca)
                .build();

        tecnicoMarcaRepository.save(tecnicoMarca);
    }

    public List<String> obtenerMarcasPorTecnico(Long idTecnico) {
        return tecnicoMarcaRepository.findByTecnico_Id(idTecnico)
                .stream()
                .map(t -> t.getMarca().getNombre())
                .collect(Collectors.toList());
    }

    public List<Long> obtenerTecnicosPorMarca(Integer idMarca) {
        return tecnicoMarcaRepository.findByMarca_IdMarca(idMarca)
                .stream()
                .map(t -> t.getTecnico().getId())
                .collect(Collectors.toList());
    }
    public void eliminarRelacion(TecnicoMarcaRequestDTO dto) {
        TecnicoMarcaId id = TecnicoMarcaId.builder()
                .id(dto.getIdTecnico())
                .idMarca(dto.getIdMarca())
                .build();

        if (!tecnicoMarcaRepository.existsById(id)) {
            throw new RuntimeException("La relación no existe.");
        }

        tecnicoMarcaRepository.deleteById(id);
    }
}
