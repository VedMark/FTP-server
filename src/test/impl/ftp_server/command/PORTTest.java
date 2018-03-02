package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import ftp_server.server.ServiceChannelException;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class PORTTest {

    @Test
    void execute_NotLoggedIn_Code530() throws UnexpectedCodeException {
        PORT pasv = new PORT(new DataTransferProcess(), "127,0,0,1,31,64");
        pasv.execute();
        String response = pasv.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code200() throws UnexpectedCodeException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        serverDTP.getParameters().setServerAddress(new InetSocketAddress("127.0.0.1", 8000));
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        PORT port = new PORT(serverDTP, "127,0,0,1,31,64");
        port.execute();
        String response = port.getResponseMessage();
        assertEquals("200 PORT command successful\r\n", response);
        assertEquals("127.0.0.1", serverDTP.getParameters().getUserAddress().getAddress().getHostAddress());
        assertEquals(8000, serverDTP.getParameters().getUserAddress().getPort());
    }
}