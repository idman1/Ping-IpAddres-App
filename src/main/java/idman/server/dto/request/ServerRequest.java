package idman.server.dto.request;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerRequest {
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
}
