/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.TecnicoMarca;
import sm.dswTaller.ms.tallerAutomotriz.model.TecnicoMarcaId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoMarcaRepository extends JpaRepository<TecnicoMarca, TecnicoMarcaId> {

    List<TecnicoMarca> findByTecnico_Id(Long idTecnico);

    List<TecnicoMarca> findByMarca_IdMarca(Integer idMarca);
}
