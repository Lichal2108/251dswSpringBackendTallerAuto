/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.dswTaller.ms.ordenServicio.model.EvidenciaTecnica;
import sm.dswTaller.ms.ordenServicio.model.OstTecnico;

/**
 *
 * @author Aldair
 */
public interface EvidenciaTecnicaRepository extends JpaRepository<EvidenciaTecnica, Long> {

    public boolean existsByOstTecnico(OstTecnico relacion);
}