
package sm.dswTaller.ms.ordenServicio.service;

import java.util.List;
import sm.dswTaller.ms.ordenServicio.dto.OstRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstResponseDTO;

/**
 *
 * @author Aldair
 */
public interface OstService {
    List<OstResponseDTO> listOsts();
    OstResponseDTO insertOst(OstRequestDTO dto);
    OstResponseDTO updateOst(OstRequestDTO dto);
    OstResponseDTO findOst(int idOst);
    void deleteOst(int id);
    List<OstResponseDTO> buscarOstPorIdPersona(Long idUsuario);
    List<OstResponseDTO> obtenerOstPorSupervisor(Long idSupervisor);
    //void actualizarInventarioYRevision(Integer idOst, InventarioRevisionDTO dto);
}
