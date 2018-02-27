package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MKDTest {
    FTPServerDTP serverDTP;
    private String ftp = "ftp";

    MKDTest() {
    }

    @BeforeEach
    void initAccount() {
        serverDTP = new FTPServerDTP();

        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        MKD mkd = new MKD(serverDTP, "ftp");
        mkd.execute();
        String response = mkd.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code257() throws UnexpectedCodeException {
        MKD mkd = new MKD(serverDTP, ftp);
        mkd.execute();
        String response = mkd.getResponseMessage();
        new RMD(serverDTP, ftp).execute();

        assertEquals("257 Your current location is /\r\n", response);
    }

    @Test
    void execute_DirExists_Code550() throws UnexpectedCodeException {
        MKD mkd = new MKD(serverDTP, ftp);
        mkd.execute();
        mkd = new MKD(serverDTP, ftp);
        mkd.execute();
        String response = mkd.getResponseMessage();
        new RMD(serverDTP, ftp).execute();

        assertEquals("550 Requested action not taken, directory exists or no parent exists\r\n", response);
    }
}