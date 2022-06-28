package madstodolist.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "manga")
public class Manga extends Libro {
    private String autor;
    private String demografia;
    private String genero;
    private boolean color;

    public Manga() {
        super();
    }

    public Manga(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion, String editorial,
                 String autor, String demografia, String genero, boolean color) {
        super(isbn, titulo, fechaPublicacion, longitudImpresion, editorial);
        this.autor = autor;
        this.demografia = demografia;
        this.genero = genero;
        this.color = color;
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
