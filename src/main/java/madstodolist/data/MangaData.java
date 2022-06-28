package madstodolist.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class MangaData {
  private String isbn;
  private String titulo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaPublicacion;
  private int LongitudImpresion;
  private String editorial;
  private String autor;
  private String demografia;
  private String genero;
  private boolean color;

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

  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getDemografia() {
    return demografia;
  }

  public void setDemografia(String demografia) {
    this.demografia = demografia;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public boolean isColor() {
    return color;
  }

  public void setColor(boolean color) {
    this.color = color;
  }
}
