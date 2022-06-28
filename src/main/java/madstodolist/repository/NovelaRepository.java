package madstodolist.repository;

import madstodolist.model.Novela;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface NovelaRepository extends CrudRepository<Novela, Long> {

    @Query("SELECT n FROM Novela n WHERE n.idLibro = ?1")
    Optional<Novela> findById(int id);

    @Query("select n from Novela n where n.isbn = ?1")
    Optional<Novela> findByIsbn(String isbn);
}
