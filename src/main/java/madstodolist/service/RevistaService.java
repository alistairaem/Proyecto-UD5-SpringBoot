package madstodolist.service;

import madstodolist.exception.RevistaNotFoundException;
import madstodolist.model.Revista;
import madstodolist.repository.RevistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class RevistaService {

  private RevistaRepository revistaRepository;

  @Autowired
  public void NovelaRepository(RevistaRepository revistaRepository) {
    this.revistaRepository = revistaRepository;
  }

  public boolean existeRevista(String isbn) {
    return revistaRepository.findByIsbn(isbn).isPresent();
  }

  //Crea una nueva revista
  @Transactional
  public Revista nuevaRevista(String isbn, String titulo, LocalDate fechaPublicacion,
                              int longitudImpresion, String editorial, String tipo) {
    Revista revista = new Revista(isbn, titulo, fechaPublicacion, longitudImpresion, editorial, tipo);
    revistaRepository.save(revista);
    return revista;
  }

  @Transactional(readOnly = true)
  public List<Revista> allRevistas() {
    List<Revista> revistas = new ArrayList<>();
    revistaRepository.findAll().forEach(revistas::add);
    return revistas;
  }

  @Transactional(readOnly = true)
  public Revista findById(int id) {
    return revistaRepository.findById(id).orElse(null);
  }

  @Transactional
  public Revista modificaRevista(int id, String titulo, LocalDate fechaPublicacion,
                                 int longitudImpresion, String editorial, String tipo) {
    Revista revista = revistaRepository.findById(id).orElse(null);

    if (revista == null) {
      throw new RevistaNotFoundException("No existe revista con id " + id);
    }

    revista.setTitulo(titulo);
    revista.setFechaPublicacion(fechaPublicacion);
    revista.setLongitudImpresion(longitudImpresion);
    revista.setEditorial(editorial);
    revista.setTipo(tipo);
    revistaRepository.save(revista);
    return revista;
  }

  @Transactional
  public void borraRevista(int idNovela) {
    Revista revista = revistaRepository.findById(idNovela).orElse(null);

    if (revista == null) {
      throw new RevistaNotFoundException("No existe revista con id " + idNovela);
    }
    revistaRepository.delete(revista);
  }

  @Transactional(readOnly = true)
  public Revista findByIsbn(String isbn) {
    return revistaRepository.findByIsbn(isbn).orElse(null);
  }


  @Transactional(readOnly = true)
  public Revista findByIdRevista(int s) {
    return revistaRepository.findById(s).orElse(null);
  }
}
