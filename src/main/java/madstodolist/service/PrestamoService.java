package madstodolist.service;

import madstodolist.model.Cliente;
import madstodolist.model.Prestamo;
import madstodolist.model.Usuario;
import madstodolist.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {


  private PrestamoRepository prestamoRepository;

  @Autowired
  public PrestamoService(PrestamoRepository prestamoRepository) {
    this.prestamoRepository = prestamoRepository;
  }
  @Transactional
  public void nuevoPrestamo(Prestamo prestamo) {
    prestamoRepository.save(prestamo);
  }

  @Transactional(readOnly = true)
  public List<Prestamo> allPrestamos() {
    List<Prestamo> prestamos = new ArrayList<>();
    prestamoRepository.findAll().forEach(prestamos::add);
    return prestamos;
  }

  @Transactional(readOnly = true)
  public Prestamo findById(int id) {
    return prestamoRepository.findById(id).orElse(null);
  }

  public List<Prestamo> prestamosSemana() {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByFechaDevolucion(LocalDate.now(), LocalDate.now().plusDays(7));
    return prestamos;
  }

  public List<Prestamo> prestamosSinDevolver() {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByDevueltoFalse();
    return prestamos;
  }
  @Transactional
  public void añadeCliente(Prestamo prestamo, Cliente cliente) {
    prestamo.setCliente(cliente);
    prestamoRepository.save(prestamo);
  }

    @Transactional
  public void borraPrestamo(Prestamo prestamo) {
    prestamoRepository.delete(prestamo);
  }

  public List<Prestamo> findPrestamosByCliente(int cliente) {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByCliente(cliente);
    return prestamos;
  }

  public List<Prestamo> prestamosSinDevolverCliente(int cliente) {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByDevueltoFalseAndCliente(cliente);
    return prestamos;
  }

    public void finalizarPrestamo(Prestamo prestamo) {
        prestamo.setDevuelto(true);
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoRepository.save(prestamo);
    }
}
