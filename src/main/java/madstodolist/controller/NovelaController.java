package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.data.NovelaData;
import madstodolist.exception.NovelaNotFoundException;
import madstodolist.model.Novela;
import madstodolist.model.Usuario;
import madstodolist.service.NovelaService;
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
public class NovelaController {

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  ManagerUserSession managerUserSession;

  @Autowired
  private NovelaService novelaService;

  @GetMapping("/{user}/novela/nueva")
  public String formNuevaNovela(@PathVariable(value = "user") String idUsuario,@ModelAttribute NovelaData novelaData,
                                Model model, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    return "formNuevaNovela";
  }

  @PostMapping("/{user}/novela/nueva")
  public String nuevoNovela(@PathVariable(value = "user") String idUsuario,@ModelAttribute NovelaData novelaData,
                            Model model, RedirectAttributes flash, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    if (novelaService.existeNovela(novelaData.getIsbn())) {
      flash.addFlashAttribute("mensaje", "Ya existe una novela con ese ISBN");
      return "formNuevaNovela";
    } else {
      novelaService.nuevaNovela(novelaData.getIsbn(), novelaData.getTitulo(), novelaData.getFechaPublicacion(),
              novelaData.getLongitudImpresion(), novelaData.getEditorial(), novelaData.getAutor(),
              novelaData.getTema(), novelaData.getSubgenero());
      flash.addFlashAttribute("mensaje", "Cliente creado con éxito");
      return "redirect:/"+ idUsuario +"/novela/lista";
    }
  }

  @GetMapping("/{user}/novela/lista")
  public String listadoNovelas(@PathVariable(value = "user") String idUsuario,Model model, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    List<Novela> novelas = novelaService.allNovelas();
    model.addAttribute("novelas", novelas);
    return "listaNovelas";
  }

  @GetMapping("/{user}/novela/{id}/editar")
  public String formEditaNovela(@PathVariable(value = "user") String idUsuario,@PathVariable(value = "id") int id,
                                @ModelAttribute NovelaData novelaData,
                                Model model, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    model.addAttribute("novela", novela);
    novelaData.setTitulo(novela.getTitulo());
    novelaData.setFechaPublicacion(novela.getFechaPublicacion());
    novelaData.setLongitudImpresion(novela.getLongitudImpresion());
    novelaData.setEditorial(novela.getEditorial());
    novelaData.setAutor(novela.getAutor());
    novelaData.setTema(novela.getTema());
    novelaData.setSubgenero(novela.getSubgenero());
    return "formEditarNovela";
  }

  @PostMapping("/{user}/novela/{id}/editar")
  public String editarNovela(@PathVariable(value = "user") String idUsuario,@PathVariable(value = "id") int id,
                             @ModelAttribute NovelaData novelaData,
                             Model model, RedirectAttributes flash, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    novelaService.modificaNovela(id, novelaData.getTitulo(), novelaData.getFechaPublicacion(),
            novelaData.getLongitudImpresion(), novelaData.getEditorial(), novelaData.getAutor(),
            novelaData.getTema(), novelaData.getSubgenero());
    flash.addFlashAttribute("mensaje", "Novela modificada correctamente");
    return "redirect:/"+ idUsuario +"/novela/lista";
  }

  @GetMapping("/{user}/novela/{id}/eliminar")
  public String formBorrarNovela(@PathVariable(value = "user") String idUsuario,@PathVariable(value = "id") int id,
                                 @ModelAttribute NovelaData novelaData,
                                 Model model, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);

    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    model.addAttribute("novela", novela);
    novelaData.setIsbn(novela.getIsbn());
    novelaData.setTitulo(novela.getTitulo());
    novelaData.setFechaPublicacion(novela.getFechaPublicacion());
    novelaData.setLongitudImpresion(novela.getLongitudImpresion());
    novelaData.setEditorial(novela.getEditorial());
    novelaData.setAutor(novela.getAutor());
    novelaData.setTema(novela.getTema());
    novelaData.setSubgenero(novela.getSubgenero());
    return "formEliminarNovela";
  }

  @PostMapping("/{user}/novela/{id}/eliminar")
  public String borrarNovela(@PathVariable(value = "user") String idUsuario,@PathVariable(value = "id") int id,
                             @ModelAttribute NovelaData novelaData,
                             Model model, RedirectAttributes flash, HttpSession session) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }

    novelaService.borraNovela(id);
    flash.addFlashAttribute("mensaje", "Novela eliminada correctamente");
    return "redirect:/"+ idUsuario +"/novela/lista";
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
