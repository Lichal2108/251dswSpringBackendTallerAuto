package sm.dswTaller.ms.ordenServicio.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sm.dswTaller.ms.ordenServicio.model.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {

    @Override
    public Optional<Pregunta> findById(Integer idPregunta);
}