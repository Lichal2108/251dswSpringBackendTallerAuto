package sm.dswTaller.ms.tallerVentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.dswTaller.ms.tallerVentas.model.Recibo;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {
} 