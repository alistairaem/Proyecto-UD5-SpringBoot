package madstodolist.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "novela")
public class Novela extends Libro {
    private String autor;
    private String tema;
    private String subgenero;

    public Novela() {
        super();
    }

    public Novela(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion, String editorial,
                  String autor, String tema, String subgenero) {
        super(isbn, titulo, fechaPublicacion, longitudImpresion, editorial);
        this.autor = autor;
        this.tema = tema;
        this.subgenero = subgenero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getSubgenero() {
        return subgenero;
    }

    public void setSubgenero(String subgenero) {
        this.subgenero = subgenero;
    }

}
