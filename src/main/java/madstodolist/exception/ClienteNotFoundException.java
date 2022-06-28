package madstodolist.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String id) {
        super("Cliente not found: " + id);
    }

}
