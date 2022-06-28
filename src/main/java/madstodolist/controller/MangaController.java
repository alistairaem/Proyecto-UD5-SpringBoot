package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.controller.exception.UsuarioNotFoundException;
import madstodolist.data.MangaData;
import madstodolist.exception.MangaNotFoundException;
import madstodolist.model.Manga;
import madstodolist.model.Usuario;
import madstodolist.service.MangaService;
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
public class MangaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;
    @Autowired
    private MangaService mangaService;

    @GetMapping("/{user}/manga/nuevo")
    public String formNuevoManga(@PathVariable(value = "user") String idUsuario, @ModelAttribute MangaData mangaData, Model model
            , HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        return "formNuevoManga";
    }

    @PostMapping("/{user}/manga/nuevo")
    public String nuevoManga(@PathVariable(value = "user") String idUsuario, @ModelAttribute MangaData mangaData, Model model, RedirectAttributes flash
            , HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        if (mangaService.existeManga(mangaData.getIsbn())) {
            flash.addFlashAttribute("mensaje", "Ya existe un manga con ese ISBN");
            return "redirect:/"+ idUsuario+ "/manga/nuevo";
        } else {
            mangaService.nuevoManga(mangaData.getIsbn(), mangaData.getTitulo(), mangaData.getFechaPublicacion(), mangaData.getLongitudImpresion(),
                    mangaData.getEditorial(), mangaData.getAutor(), mangaData.getDemografia(), mangaData.getGenero(), mangaData.isColor());
            flash.addFlashAttribute("mensaje", "Manga creado con éxito");
            return "redirect:/"+ idUsuario+ "/manga/lista";
        }
    }

    @GetMapping("/{user}/manga/lista")
    public String listadoMangas(@PathVariable(value = "user") String idUsuario, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        List<Manga> mangas = mangaService.allMangas();
        model.addAttribute("usuario", usuario);
        model.addAttribute("mangas", mangas);
        return "listaMangas";
    }

    @GetMapping("/{user}/manga/{id}/editar")
    public String formEditaManga(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id, @ModelAttribute MangaData mangaData,
                                 Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Manga manga = mangaService.findById(id);
        if (manga == null) {
            throw new MangaNotFoundException("No existe un manga con id: " + id);
        }
        model.addAttribute("manga", manga);
        mangaData.setTitulo(manga.getTitulo());
        mangaData.setFechaPublicacion(manga.getFechaPublicacion());
        mangaData.setLongitudImpresion(manga.getLongitudImpresion());
        mangaData.setEditorial(manga.getEditorial());
        mangaData.setAutor(manga.getAutor());
        mangaData.setDemografia(manga.getDemografia());
        mangaData.setGenero(manga.getGenero());
        mangaData.setColor(manga.isColor());
        return "formEditarManga";
    }

    @PostMapping("/{user}/manga/{id}/editar")
    public String editarManga(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id, @ModelAttribute MangaData mangaData,
                              Model model, RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Manga manga = mangaService.findById(id);
        if (manga == null) {
            throw new MangaNotFoundException("No existe un manga con id: " + id);
        }
        mangaService.modificaManga(id, mangaData.getTitulo(), mangaData.getFechaPublicacion(), mangaData.getLongitudImpresion(),
                mangaData.getEditorial(), mangaData.getAutor(), mangaData.getDemografia(), mangaData.getGenero(), mangaData.isColor());
        flash.addFlashAttribute("mensaje", "Manga modificado correctamente");
        return "redirect:/"+ idUsuario +"/manga/lista";
    }

    @GetMapping("/{user}/manga/{id}/eliminar")
    public String formBorrarManga(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id, @ModelAttribute MangaData mangaData,
                                  Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);

        Manga manga = mangaService.findById(id);
        if (manga == null) {
            throw new MangaNotFoundException("No existe una novela con id: " + id);
        }
        model.addAttribute("manga", manga);
        mangaData.setIsbn(manga.getIsbn());
        mangaData.setTitulo(manga.getTitulo());
        mangaData.setFechaPublicacion(manga.getFechaPublicacion());
        mangaData.setLongitudImpresion(manga.getLongitudImpresion());
        mangaData.setEditorial(manga.getEditorial());
        mangaData.setAutor(manga.getAutor());
        mangaData.setDemografia(manga.getDemografia());
        mangaData.setGenero(manga.getGenero());
        mangaData.setColor(manga.isColor());
        return "formEliminarManga";
    }

    @PostMapping("/{user}/manga/{id}/eliminar")
    public String borrarManga(@PathVariable(value = "user") String idUsuario, @PathVariable(value = "id") int id, @ModelAttribute MangaData mangaData,
                              Model model, RedirectAttributes flash, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }
        Usuario usuario = comprobarUsuarioLogeado(session, idUsuario);
        model.addAttribute("usuario", usuario);
        Manga manga = mangaService.findById(id);
        if (manga == null) {
            throw new MangaNotFoundException("No existe una novela con id: " + id);
        }

        mangaService.borraManga(id);
        flash.addFlashAttribute("mensaje", "Manga eliminado correctamente");
        return "redirect:/"+ idUsuario +"/manga/lista";
    }

    private Usuario comprobarUsuarioLogeado(HttpSession session, String idUsuario) {
        managerUserSession.comprobarUsuarioLogeado(session, idUsuario);
        Usuario usuario = usuarioService.findById(idUsuario);
        if (usuario == null){
            throw new UsuarioNotFoundException();
        }
        return usuario;
    }

}
