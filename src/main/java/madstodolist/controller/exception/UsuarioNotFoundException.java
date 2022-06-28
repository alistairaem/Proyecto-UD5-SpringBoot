package madstodolist.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Usuario no encontrado")
public class UsuarioNotFoundException extends RuntimeException {
    //redirige a la pagina de login
    public String redirectToLogin() {
        return "redirect:/login";
    }
}
