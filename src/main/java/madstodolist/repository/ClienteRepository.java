package madstodolist.repository;

import madstodolist.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.idCliente = ?1")
    Optional<Cliente> findById(int id);
    @Query("select c from Cliente c where c.dni = ?1")
    Optional<Cliente> findByDni(String dni);


}

