package madstodolist.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo", unique = true, nullable = false)
    private int idPrestamo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private LocalDate fechaDevolucion;

    private Boolean devuelto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_prestamo", joinColumns = {@JoinColumn(name = "idPrestamo")},
            inverseJoinColumns = {@JoinColumn(name = "idLibro")})
    private List<Libro> lLibros;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    public Prestamo() {
        lLibros = new ArrayList<Libro>();
    }

    public Prestamo(LocalDate fechaFin, Cliente cliente) {
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        lLibros = new ArrayList<Libro>();
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Libro> getlLibros() {
        return lLibros;
    }

    public void addLibro(Libro libro) {
        this.lLibros.add(libro);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }


    @Override
    public String toString() {
        return "Prestamo{" +
                "idPrestamo=" + idPrestamo +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", fechaDevolucion=" + fechaDevolucion +
                ", devuelto=" + devuelto +
                ", cliente=" + cliente +
                '}';
    }
}
