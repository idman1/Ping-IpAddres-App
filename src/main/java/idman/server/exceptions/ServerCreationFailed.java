package idman.server.exceptions;

public class ServerCreationFailed extends Throwable {
    private String message;

    public ServerCreationFailed(String message) {
        super(message);

    }
}
