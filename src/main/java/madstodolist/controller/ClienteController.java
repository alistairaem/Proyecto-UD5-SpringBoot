package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.data.ClienteData;
import madstodolist.exception.ClienteNotFoundException;
import madstodolist.model.Cliente;
import madstodolist.model.Prestamo;
import madstodolist.model.Usuario;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ManagerUserSession managerUserSession;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private RevistaService revistaService;
    @Autowired
    private MangaService mangaService;
    @Autowired
    private NovelaService novelaService;

    @GetMapping("/{user}/cliente/nuevo")
    public String formNuevoCliente(@PathVariable(value = "user") String idUsuario, @ModelAttribute ClienteData clienteData,
                                   Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        return "formNuevoCliente";
    }

    @PostMapping("/{user}/cliente/nuevo")
    public String nuevoCliente(@PathVariable(value = "user") String idUsuario, @ModelAttribute ClienteData clienteData,
                               Model model, RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        if (clienteService.existeCliente(clienteData.getDni())) {
            flash.addFlashAttribute("mensaje", "Ya existe un cliente con ese DNI");
            return "formNuevoCliente";
        } else {
            clienteService.nuevoCliente(clienteData.getNombre(), clienteData.getApellido(), clienteData.getDni(),
                    clienteData.getDireccion(), clienteData.getEdad());
            flash.addFlashAttribute("mensaje", "Cliente creado con éxito");
            return "redirect:/"+ idUsuario +"/cliente/lista";
        }

    }

    @GetMapping("/{user}/cliente/lista")
    public String listadoClientes(@PathVariable(value = "user") String idUsuario, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        List<Cliente> clientes = clienteService.allClientes();
        model.addAttribute("clientes", clientes);
        return "listaClientes";
    }

    @GetMapping("/{user}/cliente/{id}/editar")
    public String formEditaCliente(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                   @ModelAttribute ClienteData clienteData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            throw new ClienteNotFoundException("No existe un cliente con id: " + id);
        }

        model.addAttribute("cliente", cliente);
        clienteData.setNombre(cliente.getNombre());
        clienteData.setApellido(cliente.getApellido());
        clienteData.setDireccion(cliente.getDireccion());
        clienteData.setEdad(cliente.getEdad());
        return "formEditarCliente";
    }

    @GetMapping("/{user}/cliente/{id}/prestamos")
    public String formPrestamosCliente(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                       @ModelAttribute ClienteData clienteData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            throw new ClienteNotFoundException("No existe un cliente con id: " + id);
        }
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        for (int i = 0; i < cliente.getlPrestamos().size(); i++) {
            prestamos.add(prestamoService.findById(cliente.getlPrestamos().get(i).getIdPrestamo()));
        }
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
        model.addAttribute("prestamo", prestamos);

        return "listaPrestamosCliente";
    }

    @PostMapping("/{user}/cliente/{id}/editar")
    public String editarCliente(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                @ModelAttribute ClienteData clienteData, Model model,
                                RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            throw new ClienteNotFoundException("No existe un cliente con id: " + id);
        }

        clienteService.modificaCliente(id, clienteData.getNombre(), clienteData.getApellido(),
                clienteData.getDireccion(), clienteData.getEdad());
        flash.addFlashAttribute("mensaje", "Cliente modificado correctamente");
        return "redirect:/"+ idUsuario +"/cliente/lista";
    }

    @GetMapping("/{user}/cliente/{id}/eliminar")
    public String formBorrarCliente(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                    @ModelAttribute ClienteData clienteData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            throw new ClienteNotFoundException("No existe un cliente con id: " + id);
        }

        model.addAttribute("cliente", cliente);
        clienteData.setNombre(cliente.getNombre());
        clienteData.setApellido(cliente.getApellido());
        clienteData.setDireccion(cliente.getDireccion());
        clienteData.setDni(cliente.getDni());
        clienteData.setEdad(cliente.getEdad());
        return "formEliminarCliente";
    }

    @PostMapping("/{user}/cliente/{id}/eliminar")
    public String borrarCliente(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                @ModelAttribute ClienteData clienteData, Model model, RedirectAttributes flash,
                                HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            throw new ClienteNotFoundException("No existe un cliente con id: " + id);
        }

        clienteService.borraCliente(id);
        flash.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        return "redirect:"+ idUsuario + "/cliente/lista";
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
