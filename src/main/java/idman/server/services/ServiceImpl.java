package idman.server.services;

import idman.server.Data.models.ServerSide;
import idman.server.Data.repositories.ServerRepo;
import idman.server.dto.request.ServerRequest;
import idman.server.dto.responses.ServerResponse;
import idman.server.exceptions.IpAddressNotValid;
import idman.server.exceptions.ServerCreationFailed;
import idman.server.exceptions.ServerNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static idman.server.enums.ServerStatus.SERVER_UP;
import static idman.server.enums.ServerStatus.Server_DOWN;
import static idman.server.utils.AppUtils.buildPageRequest;
import static idman.server.utils.ExceptionsErrorsMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceImpl implements ServerSideServices {


    private final ServerRepo serverRepo;
    private final ModelMapper mapper;


    @Override
    public ServerResponse createServer(ServerRequest serverRequest) throws ServerCreationFailed {
        ServerSide serverSide = mapper.map(serverRequest, ServerSide.class);
        log.info("Saving Server with {} for Tinu", serverSide.getIpAddress());

//        boolean isValid = Validation.isValidIPAddress(serverRequest.getIpAddress());
//        if (!isValid)
//            throw new IpAddressNotValid("ipAddress is not valid");

        boolean isPresent = serverRepo.findServerSideByIpAddress(serverRequest.getIpAddress())
                .isPresent();
        if (isPresent)
            throw new ServerCreationFailed(String.format(SERVER_NOT_CREATED_ERROR_MESSAGE, serverRequest.getIpAddress()));
        serverSide.setImageUrl(setServerImageUrl());
        ServerSide savedServerSide = serverRepo.save(serverSide);
        boolean isSavedServerSide = savedServerSide.getId() != null;
        if (!isSavedServerSide)
            throw new ServerCreationFailed(String.format(SERVER_NOT_CREATED_ERROR_MESSAGE, savedServerSide.getId()));
        return serverResponseBuilder(savedServerSide);

    }


    @Override
    public ServerResponse pingServer(String ipAddress) throws IOException, ServerNotFound {
        ServerSide serverSide = serverRepo.findServerSideByIpAddress(ipAddress)
                .orElseThrow(() -> new ServerNotFound(String.format(SERVER_WITH_SUCH_IP_ADDRESS_NOT_FOUND, ipAddress)));
        InetAddress address = InetAddress.getByName(ipAddress);
        // serverSide.setStatus(Server_DOWN);
        serverSide.setStatus(address.isReachable(10000) ? SERVER_UP : Server_DOWN);
        serverRepo.save(serverSide);
        return serverResponseBuilder(serverSide);
    }

    @Override
    public List<ServerResponse> listOfAllServer(int list, int page) {
        Pageable pageable = buildPageRequest(list, page);
        Page<ServerSide> serverPage = serverRepo.findAll(pageable);
        Collection<ServerSide> serverSides = serverPage.getContent();
        return serverSides.stream().map(ServiceImpl::serverResponseBuilder).toList();

    }

    public ServerResponse findServerById(Long id) throws ServerNotFound {
        Optional<ServerSide> foundServer = serverRepo.findById(id);
        ServerSide serverSide = foundServer.orElseThrow(()->
                                new ServerNotFound(String.format(SERVER_WITH_SUCH_ID_NOT_FOUND,id)));
        return serverResponseBuilder(serverSide);
    }

    @Override
    public ServerSide findServerByIpAddress(String serverIp) {
        return null;
    }

    @Override
    public ServerResponse deleteServerWithIpAddress(String ipAddress) throws IpAddressNotValid {
      Optional<ServerSide> serverResponse =  serverRepo.findServerSideByIpAddress(ipAddress);
      ServerSide serverSide = serverResponse.orElseThrow(()->
              new IpAddressNotValid(String.format(SERVER_WITH_SUCH_IP_ADDRESS_NOT_FOUND,ipAddress)));
      serverRepo.delete(serverSide);
      return serverResponseBuilder(serverSide);
    }

    public void deleteAll() {
        serverRepo.deleteAll();
    }


    private static ServerResponse serverResponseBuilder(ServerSide serverSide) {
        return ServerResponse.builder()
                .id(serverSide.getId())
                .name(serverSide.getName())
                .memory(serverSide.getMemory())
                .ipAddress(serverSide.getIpAddress())
                .status(serverSide.getStatus())
                .type(serverSide.getType())
                .imageUrl(serverSide.getImageUrl()).build();
    }

    private String setServerImageUrl() {

        return null;
    }


}
