package madstodolist.exception;

public class MangaNotFoundException extends RuntimeException {
    public MangaNotFoundException(String id) {
        super("Manga not found: " + id);
    }
}
