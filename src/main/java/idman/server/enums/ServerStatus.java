package idman.server.enums;

public enum ServerStatus {
    SERVER_UP("SERVER UP"),
    Server_DOWN("SERVER DOWN");

    private final String status;

    ServerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
