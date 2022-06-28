package madstodolist.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "revista")
public class Revista extends Libro {
    private String tipo;

    public Revista() {
        super();
    }

    public Revista(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion, String editorial, String tipo) {
        super(isbn, titulo, fechaPublicacion, longitudImpresion, editorial);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
