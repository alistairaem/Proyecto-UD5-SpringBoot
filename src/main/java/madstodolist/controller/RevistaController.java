package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.data.RevistaData;
import madstodolist.exception.RevistaNotFoundException;
import madstodolist.model.Revista;
import madstodolist.model.Usuario;
import madstodolist.service.RevistaService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RevistaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    private RevistaService revistaService;

    @GetMapping("/{user}/revista/nueva")
    public String formNuevaRevista(@PathVariable(value = "user") String idUsuario, @ModelAttribute RevistaData revistaData,
                                   Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        return "formNuevaRevista";
    }

    @PostMapping("/{user}/revista/nueva")
    public String nuevaRevista(@PathVariable(value = "user") String idUsuario, @ModelAttribute RevistaData revistaData,
                               Model model, RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        if (revistaService.existeRevista(revistaData.getIsbn())) {
            flash.addFlashAttribute("mensaje", "Ya existe una revista con ese ISBN");
            return "formNuevaRevista";
        } else {
            revistaService.nuevaRevista(revistaData.getIsbn(), revistaData.getTitulo(), revistaData.getFechaPublicacion(),
                    revistaData.getLongitudImpresion(), revistaData.getEditorial(), revistaData.getTipo());
            flash.addFlashAttribute("mensaje", "Revista creado con éxito");
            return "redirect:/"+ idUsuario +"/revista/lista";
        }
    }

    @GetMapping("/{user}/revista/lista")
    public String listadoNovelas(@PathVariable(value = "user") String idUsuario, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        List<Revista> revistas = revistaService.allRevistas();
        model.addAttribute("revistas", revistas);
        return "listaRevistas";
    }

    @GetMapping("/{user}/revista/{id}/editar")
    public String formEditaNovela(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                  @ModelAttribute RevistaData revistaData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Revista revista = revistaService.findById(id);
        if (revista == null) {
            throw new RevistaNotFoundException("No existe una revista con id: " + id);
        }
        model.addAttribute("revista", revista);
        revistaData.setTitulo(revista.getTitulo());
        revistaData.setFechaPublicacion(revista.getFechaPublicacion());
        revistaData.setLongitudImpresion(revista.getLongitudImpresion());
        revistaData.setEditorial(revista.getEditorial());
        revistaData.setTipo(revista.getTipo());
        return "formEditarRevista";
    }

    @PostMapping("/{user}/revista/{id}/editar")
    public String editarRevista(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                @ModelAttribute RevistaData revistaData, Model model, RedirectAttributes flash,
                                HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Revista revista = revistaService.findById(id);
        if (revista == null) {
            throw new RevistaNotFoundException("No existe una revista con id: " + id);
        }
        revistaService.modificaRevista(id, revistaData.getTitulo(), revistaData.getFechaPublicacion(),
                revistaData.getLongitudImpresion(), revistaData.getEditorial(), revistaData.getTipo());
        flash.addFlashAttribute("mensaje", "revista modificada correctamente");
        return "redirect:/"+ idUsuario +"/revista/lista";
    }

    @GetMapping("/{user}/revista/{id}/eliminar")
    public String formBorrarRevista(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                    @ModelAttribute RevistaData revistaData, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }

        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Revista revista = revistaService.findById(id);
        if (revista == null) {
            throw new RevistaNotFoundException("No existe una revista con id: " + id);
        }
        model.addAttribute("revista", revista);
        revistaData.setIsbn(revista.getIsbn());
        revistaData.setTitulo(revista.getTitulo());
        revistaData.setFechaPublicacion(revista.getFechaPublicacion());
        revistaData.setLongitudImpresion(revista.getLongitudImpresion());
        revistaData.setEditorial(revista.getEditorial());
        revistaData.setTipo(revista.getTipo());
        return "formEliminarRevista";
    }

    @PostMapping("/{user}/revista/{id}/eliminar")
    public String borrarRevista(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id,
                                @ModelAttribute RevistaData revistaData, Model model, RedirectAttributes flash,
                                HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Revista revista = revistaService.findById(id);
        if (revista == null) {
            throw new RevistaNotFoundException("No existe una revista con id: " + id);
        }

        revistaService.borraRevista(id);
        flash.addFlashAttribute("mensaje", "revista eliminada correctamente");
        return "redirect:/"+ idUsuario +"/revista/lista";
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
