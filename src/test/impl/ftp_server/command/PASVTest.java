package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class PASVTest {

    @Test
    void execute_NotLoggedIn_Code530() throws UnexpectedCodeException {
        PASV pasv = new PASV(new FTPServerDTP());
        pasv.execute();
        String response = pasv.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code227() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        serverDTP.getParameters().setServerAddress(new InetSocketAddress("127.0.0.1", 8000));
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        InetSocketAddress serverAddress = serverDTP.getParameters().getServerAddress();
        String[] arr = serverAddress.getAddress().getHostAddress().split("\\.");
        String el4 = String.valueOf(serverAddress.getPort() / 256);
        String el5 = String.valueOf(serverAddress.getPort() % 256);

        PASV pasv = new PASV(serverDTP);
        pasv.execute();
        String response = pasv.getResponseMessage();
        assertEquals(String.format(
                "227 Entering Passive Mode(%s,%s,%s,%s,%s,%s)\r\n", arr[0], arr[1], arr[2], arr[3], el4, el5),
                response
        );
    }
}