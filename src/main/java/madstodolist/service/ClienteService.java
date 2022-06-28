package madstodolist.service;


import madstodolist.exception.ClienteNotFoundException;
import madstodolist.model.Cliente;
import madstodolist.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
  private ClienteRepository clienteRepository;

  @Autowired
  public ClienteService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  //Crea un nuevo cliente
  @Transactional
  public Cliente nuevoCliente(String nombre, String apellido, String dni, String direccion, int edad) {
    Cliente cliente = new Cliente(nombre, apellido, dni, direccion, edad);
    clienteRepository.save(cliente);
    return cliente;
  }

  //Busca todos los clientes
  @Transactional(readOnly = true)
  public List<Cliente> allClientes() {
    List<Cliente> clientes = new ArrayList<>();
    clienteRepository.findAll().forEach(clientes::add);
    return clientes;
  }

  //Modifica un cliente
  @Transactional
  public Cliente modificaCliente(int id, String nombre, String apellido, String direccion, int edad) {
    Cliente cliente = clienteRepository.findById(id).orElse(null);
    if (cliente == null) {
      throw new ClienteNotFoundException("No existe cliente con id " + id);
    }
    cliente.setNombre(nombre);
    cliente.setApellido(apellido);
    cliente.setDireccion(direccion);
    cliente.setEdad(edad);
    clienteRepository.save(cliente);
    return cliente;
  }
  @Transactional (readOnly = true)
  //Existe con el mismo dni
  public boolean existeCliente(String dni) {
    return clienteRepository.findByDni(dni).isPresent();
    }
  @Transactional (readOnly = true)
  public Cliente findByDni(String dni) {
    return clienteRepository.findByDni(dni).orElse(null);
  }

  @Transactional (readOnly = true)
  public Cliente findById(int id) {
    return clienteRepository.findById(id).orElse(null);
  }

  @Transactional
  public void borraCliente(int idCliente) {
    Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
    if (cliente == null) {
      throw new ClienteNotFoundException("No existe cliente con id " + idCliente);
    }
    clienteRepository.delete(cliente);
  }

  @Transactional
  public void añadePrestamo(Cliente cliente) {
    clienteRepository.save(cliente);
  }
}

