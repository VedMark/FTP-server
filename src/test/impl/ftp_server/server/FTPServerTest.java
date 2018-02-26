package ftp_server.server;

import ftp_server.ApplicationTests;
import ftp_server.view.LogComponent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FTPServerTest extends ApplicationTests {
    private FTPServer ftpServer;

    @BeforeEach
    void initLogComponent() throws Exception {
        this.ftpServer = new FTPServer();
    }

    @Test
    void destroy() {
        this.ftpServer.destroy();
    }

    @Test
    void setView() {
        this.ftpServer.setView(new LogComponent());
    }

    @AfterEach
    void destroyServer() {
        this.ftpServer.destroy();
    }
}
