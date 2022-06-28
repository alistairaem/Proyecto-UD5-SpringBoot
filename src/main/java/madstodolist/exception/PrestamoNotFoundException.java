package madstodolist.exception;

public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(String id) {
        super("Prestamo not found: " + id);
    }
}
