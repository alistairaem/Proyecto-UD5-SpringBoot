package madstodolist.repository;

import madstodolist.model.Cliente;
import madstodolist.model.Prestamo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends CrudRepository<Prestamo, Long> {

    @Query("SELECT p FROM Prestamo p WHERE p.idPrestamo = ?1")
    Optional<Prestamo> findById(int id);
    @Query(value = "SELECT * FROM prestamo WHERE fecha_fin BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Prestamo> findByFechaDevolucion(LocalDate now, LocalDate plusDays);


    @Query(value = "SELECT * FROM prestamo WHERE devuelto = false", nativeQuery = true)
    List<Prestamo> findByDevueltoFalse();

    @Query(value = "SELECT * FROM prestamo WHERE id_cliente = ?1", nativeQuery = true)
    List<Prestamo> findByCliente(int cliente);

    @Query(value = "SELECT * FROM prestamo WHERE id_cliente = ?1 AND devuelto = false", nativeQuery = true)
    List<Prestamo> findByDevueltoFalseAndCliente(int cliente);
}
