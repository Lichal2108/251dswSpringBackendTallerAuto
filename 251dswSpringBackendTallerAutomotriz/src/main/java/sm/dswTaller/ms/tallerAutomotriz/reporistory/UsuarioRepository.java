package sm.dswTaller.ms.tallerAutomotriz.reporistory;

import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByNombreUsuario(String email);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    public Object findById(Integer idUsuario);
}