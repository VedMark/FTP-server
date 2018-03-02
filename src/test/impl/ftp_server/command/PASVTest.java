package ftp_server.command;

import ftp_server.server.ServerProperties;
import ftp_server.server.DataTransferProcess;
import ftp_server.server.ServiceChannelException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class PASVTest {
    private Socket client;
    private Thread thread;

    @Test
    void execute_NotLoggedIn_Code530() throws UnexpectedCodeException {
        PASV pasv = new PASV(new DataTransferProcess());
        try {
            pasv.execute();
        } catch (ServiceChannelException e) {
            fail("service channel exception");
        }
        String response = pasv.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code227() throws UnexpectedCodeException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        serverDTP.getParameters().setServerAddress(new InetSocketAddress(8000));
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        InetSocketAddress serverAddress = serverDTP.getParameters().getServerAddress();
        String[] arr = serverAddress.getAddress().getHostAddress().split("\\.");
        String el4 = String.valueOf(serverAddress.getPort() / 256);
        String el5 = String.valueOf(serverAddress.getPort() % 256);

        runSimpleClient();
        PASV pasv = new PASV(serverDTP);
        try {
            pasv.execute();
        } catch (ServiceChannelException e) {
            fail("service channel exception");
        }
        String response = pasv.getResponseMessage();
        try {
            if (client != null)
                client.close();
        } catch (IOException ignored) { }
        thread.interrupt();
        assertEquals(String.format(
                "227 Entering Passive ModeEnum(0,0,0,0,31,64)\r\n", arr[0], arr[1], arr[2], arr[3], el4, el5),
                response
        );
    }

    private void runSimpleClient() {
        thread = new Thread("Test Client") {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    client = new Socket(InetAddress.getByName(null).getHostAddress(), 8000);
                } catch (Exception e) {
                    fail("Could not run client");
                }
            }
        };
        thread.start();
    }
}