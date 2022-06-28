package madstodolist.controller;


import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.model.Usuario;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  ManagerUserSession managerUserSession;
  @GetMapping("/{id}/inicio")
  public String index(@PathVariable(value="id") String idUsuario, HttpSession session, Model model) {
    if (session.getAttribute("idUsuarioLogeado") == null) {
      return "redirect:/login";
    }
    Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
    model.addAttribute("usuario", usuario);
    return "main";
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
