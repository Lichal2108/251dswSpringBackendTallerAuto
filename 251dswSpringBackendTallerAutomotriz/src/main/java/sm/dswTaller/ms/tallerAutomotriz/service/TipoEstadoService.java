
package sm.dswTaller.ms.tallerAutomotriz.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.tallerAutomotriz.dto.TipoEstadoDTO;
import sm.dswTaller.ms.tallerAutomotriz.reporistory.TipoEstadoRepository;

/**
 *
 * @author Aldair
 */
@Service
public class TipoEstadoService {

    @Autowired private TipoEstadoRepository estadoRepository;

    public List<TipoEstadoDTO> listarTodos() {
        return estadoRepository.findAll().stream()
                .map(estado -> TipoEstadoDTO.builder()
                        .id(estado.getIdEstado())
                        .estado(estado.getEstado())
                        .build())
                .collect(Collectors.toList());
    }
}