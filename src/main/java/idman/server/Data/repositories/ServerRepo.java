package idman.server.Data.repositories;

import idman.server.Data.models.ServerSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ServerRepo extends JpaRepository<ServerSide,Long> {
    Optional<ServerSide> findServerSideByIpAddress(String ipAddress);
}
