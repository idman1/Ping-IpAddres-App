package idman.server.services;

import idman.server.Data.models.ServerSide;
import idman.server.dto.request.ServerRequest;
import idman.server.dto.responses.ServerResponse;
import idman.server.exceptions.IpAddressNotValid;
import idman.server.exceptions.ServerCreationFailed;
import idman.server.exceptions.ServerNotFound;

import java.io.IOException;
import java.util.List;

public interface ServerSideServices {
    ServerResponse createServer(ServerRequest serverRequest) throws ServerCreationFailed;

    ServerResponse pingServer(String ping) throws IOException, ServerNotFound;

    List<ServerResponse> listOfAllServer(int list , int page);

    ServerSide findServerByIpAddress(String serverIp);
    ServerResponse deleteServerWithIpAddress(String ipAddress) throws IpAddressNotValid;
    ServerResponse findServerById(Long id) throws ServerNotFound;

}
