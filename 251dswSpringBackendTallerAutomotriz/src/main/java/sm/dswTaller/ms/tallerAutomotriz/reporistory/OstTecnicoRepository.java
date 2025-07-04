/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.OstTecnico;
import sm.dswTaller.ms.tallerAutomotriz.model.OstTecnicoId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OstTecnicoRepository extends JpaRepository<OstTecnico, OstTecnicoId> {
    Optional<OstTecnico> findById_IdOstAndId_IdTecnico(Integer idOst, Long idTecnico);

    List<OstTecnico> findById_IdOst(Integer idOst);

    List<OstTecnico> findById_IdTecnico(Long idTecnico);
}
