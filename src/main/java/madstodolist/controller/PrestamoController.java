package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.data.PrestamoData;
import madstodolist.data.RevistaData;
import madstodolist.exception.PrestamoNotFoundException;
import madstodolist.exception.RevistaNotFoundException;
import madstodolist.model.*;
import madstodolist.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PrestamoController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;
    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private RevistaService revistaService;

    @Autowired
    private MangaService mangaService;

    @Autowired
    private NovelaService novelaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{user}/prestamo/nuevo")
    public String formNuevoPrestamo(@PathVariable(value = "user") String idUsuario,
                                    @ModelAttribute PrestamoData prestamoData, Model model, HttpSession session) {
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        return "formNuevoPrestamo";
    }

    @PostMapping("/{user}/prestamo/nuevo")
    public String nuevoPrestamo(@PathVariable(value = "user") String idUsuario, @ModelAttribute PrestamoData prestamoData,
                                Model model, RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        if (prestamoData.getListaLibros() == null) {
            flash.addFlashAttribute("error", "Debe seleccionar al menos un libro");
            return "redirect:/"+ idUsuario + "/prestamo/nuevo";
        } else {
            Prestamo prestamo = new Prestamo();
            String[] libros = prestamoData.getListaLibros().split(",");
            for (String libro : libros) {
                if (novelaService.existeNovela(libro)) {
                    Novela novela = novelaService.findByIsbn(libro);
                    prestamo.addLibro(novela);
                } else if (revistaService.existeRevista(libro)) {
                    Revista revista = revistaService.findByIsbn(libro);
                    prestamo.addLibro(revista);
                } else if (mangaService.existeManga(libro)) {
                    Manga manga = mangaService.findByIsbn(libro);
                    prestamo.addLibro(manga);
                }
            }
            if (prestamo.getlLibros().size() == 0) {
                flash.addFlashAttribute("error", "No existe ningun libro con ese ISBN");
                return "redirect:/prestamo/nuevo";
            }
            prestamo.setDevuelto(false);
            prestamo.setFechaInicio(LocalDate.now());
            prestamo.setFechaFin(LocalDate.now().plusDays(15));
            prestamoService.nuevoPrestamo(prestamo);
            Cliente cliente = clienteService.findByDni(prestamoData.getDni());
            if (cliente != null) {
                cliente.addPrestamo(prestamo);
                clienteService.añadePrestamo(cliente);
                prestamo.setCliente(cliente);
                prestamoService.añadeCliente(prestamo, cliente);
                flash.addFlashAttribute("mensaje", "Prestamo creado con éxito");
                return "redirect:/"+ idUsuario + "/prestamo/lista";
            } else {
                prestamoService.borraPrestamo(prestamo);
                flash.addFlashAttribute("error", "No existe ningun cliente con ese dni");
                return "redirect:/"+ idUsuario + "/prestamo/nuevo";
            }
        }
    }

    @GetMapping("/{user}/prestamo/lista")
    public String listadoPrestamos(@PathVariable(value = "user") String idUsuario, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        List<Prestamo> prestamos = null;
        if (usuario.getAdmin() == 1){
            prestamos = prestamoService.allPrestamos();
        }else{
            prestamos = prestamoService.findPrestamosByCliente(usuario.getCliente());
        }
        listadoLibros(model, prestamos);
        return "listaPrestamos";
    }

    @GetMapping("/{user}/prestamo/lista/semana")
    public String listadoPrestamosSemana(@PathVariable(value = "user") String idUsuario, Model model,
                                         HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        List<Prestamo> prestamos = prestamoService.prestamosSemana();
        listadoLibros(model, prestamos);
        return "listaPrestamosSemana";
    }

    @GetMapping("/{user}/prestamo/lista/sindevolver")
    public String listadoPrestamosSinDevolver(@PathVariable(value = "user") String idUsuario, Model model,
                                              HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        List<Prestamo> prestamos = null;
        if (usuario.getAdmin() == 1){
            prestamos = prestamoService.prestamosSinDevolver();
        }else{
            prestamos = prestamoService.prestamosSinDevolverCliente(usuario.getCliente());
        }
        listadoLibros(model, prestamos);
        return "listaPrestamosSinDevolver";
    }

    private void listadoLibros(Model model, List<Prestamo> prestamos) {
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getlLibros().size() > 0) {
                for (int j = 0; j < prestamos.get(i).getlLibros().size(); j++) {
                    if (novelaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()) != null) {
                        prestamos.get(i).getlLibros().get(j).setTitulo(
                                novelaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()).getTitulo());
                    } else if (revistaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()) != null) {
                        prestamos.get(i).getlLibros().get(j).setTitulo(
                                revistaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()).getTitulo());
                    } else if (mangaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()) != null) {
                        prestamos.get(i).getlLibros().get(j).setTitulo(
                                mangaService.findById(prestamos.get(i).getlLibros().get(j).getIdLibro()).getTitulo());
                    }
                }
            }
        }
        model.addAttribute("prestamos", prestamos);
    }

    @GetMapping("/{user}/prestamo/{id}/finalizar")
    public String formDevolverPrestamo(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                    @ModelAttribute PrestamoData prestamoData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }

        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Prestamo prestamo = prestamoService.findById(id);
        if (prestamo == null) {
            throw new PrestamoNotFoundException("No existe un prestamo con id: " + id);
        }
        model.addAttribute("prestamo", prestamo);
        prestamoData.setFechaFin(prestamo.getFechaFin());
        prestamoData.setFechaInicio(prestamo.getFechaInicio());
        return "finalizaPrestamo";
    }

    @PostMapping("/{user}/prestamo/{id}/finalizar")
    public String finalizarPrestamo(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                @ModelAttribute RevistaData revistaData, Model model, RedirectAttributes flash,
                                HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Prestamo prestamo = prestamoService.findById(id);
        if (prestamo == null) {
            throw new PrestamoNotFoundException("No existe un prestamo con id: " + id);
        }

        prestamoService.finalizarPrestamo(prestamo);
        flash.addFlashAttribute("mensaje", "prestamo finalizado correctamente");
        return "redirect:/"+ idUsuario +"/prestamo/lista";
    }
    private Usuario comprobarUsuarioLogeado(HttpSession session, String idUsuario) {

        managerUserSession.comprobarUsuarioLogeado(session, idUsuario);

        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null) {
            throw new UsuarioNotFoundException();
        }
        return usuario;
    }
}

