package idman.server.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponseWithId {
    private Long id;
    private String message;
}
