package madstodolist.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RevistaData {
  private String isbn;
  private String titulo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaPublicacion;
  private int LongitudImpresion;
  private String editorial;
  private String tipo;

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public LocalDate getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(LocalDate fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public int getLongitudImpresion() {
    return LongitudImpresion;
  }

  public void setLongitudImpresion(int longitudImpresion) {
    LongitudImpresion = longitudImpresion;
  }

  public String getEditorial() {
    return editorial;
  }

  public void setEditorial(String editorial) {
    this.editorial = editorial;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
