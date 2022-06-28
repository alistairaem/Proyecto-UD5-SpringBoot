package madstodolist.repository;

import madstodolist.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String s);

    Optional<Usuario> findById(String s);


}
