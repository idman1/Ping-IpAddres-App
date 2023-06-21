package idman.server.services;

import idman.server.dto.request.ServerRequest;
import idman.server.dto.responses.ServerResponse;
import idman.server.enums.ServerStatus;
import idman.server.exceptions.ServerCreationFailed;
import idman.server.exceptions.ServerNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static java.math.BigInteger.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ServiceImplTest {

    @Autowired
    private  ServiceImpl service;
    private  ServerRequest serverRequest;
    private  ServerResponse serverResponse;


    @BeforeEach
    void setUp() throws ServerCreationFailed {
        service.deleteAll();
        serverResponse = new ServerResponse();
        serverRequest = new ServerRequest();
        serverRequest.setName("Semicolon");
        serverRequest.setType("Server");
        serverRequest.setMemory("30GB");
        serverRequest.setIpAddress("192.168.10.111");
       serverResponse = service.createServer(serverRequest);
    }
    @Test
    public void testThatServerCanBeCreated(){
        assertThat(serverResponse).isNotNull();
    }


    @Test
    public void testThatServerCanReplyWhenPinged() throws IOException, ServerNotFound {

        var foundIpAddress = service.pingServer(serverResponse.getIpAddress());
        assertThat(foundIpAddress).isNotNull();
        assertThat(foundIpAddress.getIpAddress()).isNotNull();
        assertThat(foundIpAddress.getStatus()).isNotNull();
        assertThat(foundIpAddress.getStatus()).isEqualTo(ServerStatus.SERVER_UP);
    }

    @Test

    public void testThatAllServerCanBeFound() throws ServerCreationFailed {
//        serverRequest.setIpAddress("192.168.1.2");
//        serverRequest.setName("client");
        ServerRequest serverRequest1 = new ServerRequest();
        serverRequest1.setName("Sokoto");
        serverRequest1.setIpAddress("192.168.1.1");
        service.createServer(serverRequest1);
        List<ServerResponse> serverResponses = service.listOfAllServer(1, TEN.intValue());
        assertThat(serverResponses.size()).isEqualTo(2);
    }
    @Test
    public void testThatSeverCanBeFoundWithId() throws ServerNotFound {
        var foundServer = service.findServerById(serverResponse.getId());
        assertThat(foundServer).isNotNull();
        assertThat(foundServer.getId()).isNotNull();
        assertThat(foundServer.getIpAddress()).isEqualTo(serverResponse.getIpAddress());

    }



}