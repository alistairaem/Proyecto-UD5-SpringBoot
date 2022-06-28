package madstodolist.repository;

import madstodolist.model.Revista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RevistaRepository extends CrudRepository<Revista, Long> {

    @Query("SELECT r FROM Revista r WHERE r.idLibro = ?1")
    Optional<Revista> findById(int id);
    @Query("select r from Revista r where r.isbn = ?1")
    Optional<Revista> findByIsbn(String isbn);
}
