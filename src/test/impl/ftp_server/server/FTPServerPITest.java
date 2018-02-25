package ftp_server.server;

import ftp_server.ApplicationTests;
import ftp_server.view.LogComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

class FTPServerPITest extends ApplicationTests {
    private FTPServerPI serverPI;
    private Socket client;
    private ServerSocket server;

    @Test
    void start() throws IOException {
        runSimpleClient();

        server = new ServerSocket(8000, 0, InetAddress.getByName(null));
        Socket s = server.accept();

        this.serverPI = new FTPServerPI(s, new LogComponent());
        stopServerAfter(5000);
        this.serverPI.start();
    }

    @Test
    void stop() throws IOException {
        runSimpleClient();

        server = new ServerSocket(8000, 0, InetAddress.getByName(null));
        Socket s = server.accept();

        this.serverPI = new FTPServerPI(s, new LogComponent());
        stopServerAfter(2000);
        this.serverPI.start();

        this.serverPI.stop();
    }

    private void runSimpleClient() {
        new Thread("Test Client") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    client = new Socket(InetAddress.getByName(null).getHostAddress(), 8000);
                }
                catch (Exception e) {
                    fail("Could not run client");
                }
            }
        }.start();
    }

    @Test
    void run() throws Exception {
        Thread t = new Thread("Test Client") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    client = new Socket(InetAddress.getByName(null).getHostAddress(), 8000);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String message = reader.readLine();
                    assertEquals("220-----------Welcome to FTP-server----------", message);
                    message = reader.readLine();
                    assertEquals("220-You will be disconnected after 0 minutes of inactivity", message);
                    message = reader.readLine();
                    assertEquals("220 Service ready", message);
                }
                catch (Exception e) {
                    fail("Could not run client");
                }
            }
        };
        t.start();

        server = new ServerSocket(8000, 0, InetAddress.getByName(null));
        Socket s = server.accept();

        this.serverPI = new FTPServerPI(s, new LogComponent());

        stopServerAfter(2000);

        this.serverPI.run();
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