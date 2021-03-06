package ftp_server.server;

import ftp_server.ApplicationTests;
import ftp_server.view.LogComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolInterpreterTest extends ApplicationTests {
    private ProtocolInterpreter serverPI;
    private Socket client;
    private ServerSocket server;

    @Test
    void start() throws IOException {
        runSimpleClient();

        server = new ServerSocket(8000, 0, InetAddress.getByName(null));
        Socket s = server.accept();

        try {
            this.serverPI = new ProtocolInterpreter(s, new LogComponent());
        } catch (ServiceChannelException e) {
            fail("Could not start Server PI");
        }
        this.serverPI.start();
        stopServerAfter(500);
    }

    private void runSimpleClient() {
        new Thread("Test Client") {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    client = new Socket(InetAddress.getByName(null).getHostAddress(), 8000);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String message = reader.readLine();
                    assertEquals("220-----------Welcome to FTP-server----------", message);
                    message = reader.readLine();
                    assertEquals("220-You will be disconnected after 10 minutes of inactivity", message);
                    message = reader.readLine();
                    assertEquals("220 Service ready", message);
                }
                catch (Exception e) {
                    fail("Could not run client");
                }
            }
        }.start();
    }

    private void stopServerAfter(Integer ms) {
        new Thread (()-> {
            try {
                Thread.sleep(ms);
                this.serverPI.stop();
            } catch (Exception e) {
                fail("Thread exception");
            }
        }).start();
    }

    @AfterEach
    void destroyServerPI() throws Exception {
        this.client.close();
        this.server.close();
    }
}