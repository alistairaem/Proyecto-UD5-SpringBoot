package madstodolist.data;

import java.util.ArrayList;

public class ClienteData {
  public String direccion;
  ArrayList<Integer> listaPrestamos;
  private String nombre;
  private String apellido;
  private String dni;
  private int edad;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public ArrayList<Integer> getListaPrestamos() {
    return listaPrestamos;
  }

  public void addListaPrestamos(int idPrestamos) {
    this.listaPrestamos.add(idPrestamos);
  }
}
