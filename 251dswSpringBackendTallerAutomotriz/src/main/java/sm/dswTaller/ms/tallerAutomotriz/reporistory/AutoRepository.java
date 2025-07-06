/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {

    public Auto findByPlaca(String placa);
    List<Auto> findByPersona_IdPersona(Integer idPersona);

    public Object findByIdAuto(Long id);
}
