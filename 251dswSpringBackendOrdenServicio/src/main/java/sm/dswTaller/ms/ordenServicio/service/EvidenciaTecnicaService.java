
package sm.dswTaller.ms.ordenServicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sm.dswTaller.ms.ordenServicio.dto.EvidenciaRequestDTO;
import sm.dswTaller.ms.ordenServicio.model.EvidenciaTecnica;
import sm.dswTaller.ms.ordenServicio.model.OstTecnico;
import sm.dswTaller.ms.ordenServicio.model.OstTecnicoId;
import sm.dswTaller.ms.ordenServicio.repository.EvidenciaTecnicaRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstTecnicoRepository;

/**
 *
 * @author NICOL
 */
@Service
public class EvidenciaTecnicaService {

    @Autowired private EvidenciaTecnicaRepository evidenciaRepo;
    @Autowired private OstTecnicoRepository ostTecnicoRepo;

    public void guardarEvidencia(MultipartFile archivo, EvidenciaRequestDTO dto) {
        OstTecnicoId id = new OstTecnicoId(dto.getIdOst(), dto.getIdTecnico());

        OstTecnico ostTecnico = ostTecnicoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("OST-Técnico no encontrado"));

        // Guardar el archivo en local o S3 - simulado
        String nombreArchivo = archivo.getOriginalFilename();
        String urlArchivo = "/evidencias/" + nombreArchivo; // Simulación

        EvidenciaTecnica evidencia = EvidenciaTecnica.builder()
            .idOst(dto.getIdOst())
            .idTecnico(dto.getIdTecnico())
            .ostTecnico(ostTecnico)
            .nombreArchivo(nombreArchivo)
            .url(urlArchivo)
            .descripcion(dto.getDescripcion())
            .build();

        evidenciaRepo.save(evidencia);
    }
}
