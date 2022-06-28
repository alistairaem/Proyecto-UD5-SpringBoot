package madstodolist.repository;

import madstodolist.model.Manga;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MangaRepository extends CrudRepository<Manga, Long> {

    @Query("select m from Manga m where m.idLibro = ?1")
    Optional<Manga> findById(int id);
    @Query("select m from Manga m where m.isbn = ?1")
    Optional<Manga> findByIsbn(String isbn);
}
