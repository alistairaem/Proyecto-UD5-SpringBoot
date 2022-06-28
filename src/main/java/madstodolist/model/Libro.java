package madstodolist.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "libro")
public abstract class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "idLibro", unique = true, nullable = false)
    private int idLibro;
    private String isbn;
    private String titulo;
    private LocalDate fechaPublicacion;
    private int LongitudImpresion;
    private String editorial;

    @ManyToMany(mappedBy = "lLibros", fetch = FetchType.EAGER)
    private List<Prestamo> lPrestamos;


    public Libro() {
        lPrestamos = new ArrayList<Prestamo>();
    }

    public Libro(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion, String editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        LongitudImpresion = longitudImpresion;
        this.editorial = editorial;
        lPrestamos = new ArrayList<Prestamo>();
    }

    public int getIdLibro() {
        return idLibro;
    }

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

    public List<Prestamo> getlPrestamos() {
        return lPrestamos;
    }

    public void setPrestamo(Prestamo prestamo) {
        lPrestamos.add(prestamo);
    }


    @Override
    public String toString() {
        return getTitulo();
    }
}
