package idman.server.Data.models;

import idman.server.enums.ServerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerSide {
    @Id
    @SequenceGenerator(
            name = "ServerSide",
            sequenceName = "ServerSide",
            allocationSize = 1

    )
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @NotEmpty(message = "IP address can be empty")
    @Column(unique = true)
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private ServerStatus status;
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @Override
    public String toString() {
        return "ServerSide{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", name='" + name + '\'' +
                ", memory='" + memory + '\'' +
                ", type='" + type + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
