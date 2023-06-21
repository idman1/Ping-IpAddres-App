package idman.server.dto.responses;

import idman.server.enums.ServerStatus;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse {
    private Long id;
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private ServerStatus status;

}
