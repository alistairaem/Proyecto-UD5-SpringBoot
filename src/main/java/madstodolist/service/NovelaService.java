package madstodolist.service;

import madstodolist.exception.NovelaNotFoundException;
import madstodolist.model.Novela;
import madstodolist.repository.NovelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NovelaService {

  private NovelaRepository novelaRepository;

  @Autowired
  public void NovelaRepository(NovelaRepository novelaRepository) {
    this.novelaRepository = novelaRepository;
  }

  public boolean existeNovela(String isbn) {
    return novelaRepository.findByIsbn(isbn).isPresent();
  }

  //Crea una nueva novela
  @Transactional
  public Novela nuevaNovela(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion,
                            String editorial, String autor, String tema, String subgenero) {
    Novela novela = new Novela(isbn, titulo, fechaPublicacion, longitudImpresion, editorial, autor, tema, subgenero);
    novelaRepository.save(novela);
    return novela;
  }

  @Transactional(readOnly = true)
  public List<Novela> allNovelas() {
    List<Novela> novelas = new ArrayList<>();
    novelaRepository.findAll().forEach(novelas::add);
    return novelas;
  }

  @Transactional(readOnly = true)
  public Novela findById(int id) {
    return novelaRepository.findById(id).orElse(null);
  }

  @Transactional
  public Novela modificaNovela(int id, String titulo, LocalDate fechaPublicacion, int longitudImpresion,
                               String editorial, String autor, String tema, String subgenero) {
    Novela novela = novelaRepository.findById(id).orElse(null);

    if (novela == null) {
      throw new NovelaNotFoundException("No existe novela con id " + id);
    }

    novela.setTitulo(titulo);
    novela.setFechaPublicacion(fechaPublicacion);
    novela.setLongitudImpresion(longitudImpresion);
    novela.setEditorial(editorial);
    novela.setAutor(autor);
    novela.setTema(tema);
    novela.setSubgenero(subgenero);
    novelaRepository.save(novela);
    return novela;
  }

  @Transactional
  public void borraNovela(int idNovela) {
    Novela novela = novelaRepository.findById(idNovela).orElse(null);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe novela con id " + idNovela);
    }
    novelaRepository.delete(novela);
  }

  @Transactional(readOnly = true)
  public Novela findByIsbn(String isbn) {
    return novelaRepository.findByIsbn(isbn).orElse(null);
  }

  @Transactional(readOnly = true)
  public Novela findByIdNovela(int s) {
    return novelaRepository.findById(s).orElse(null);
  }
}
