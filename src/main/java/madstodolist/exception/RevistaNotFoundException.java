package madstodolist.exception;

public class RevistaNotFoundException extends RuntimeException {
    public RevistaNotFoundException(String id) {
        super("Revista not found: " + id);
    }
}
