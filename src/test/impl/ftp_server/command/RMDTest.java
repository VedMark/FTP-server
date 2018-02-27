package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RMDTest {
    FTPServerDTP serverDTP;
    private File file;
    private String ftp;

    @BeforeEach
    void initAccount() {
        serverDTP = new FTPServerDTP();

        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        ftp = "ftp";
        String path = serverDTP.getParameters().getHome() + "/" + ftp;
        file = new File(path);
        if(!file.mkdir()) {
            fail("Could not create directory");
        }
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        RMD rmd = new RMD(serverDTP, "ftp");
        rmd.execute();
        String response = rmd.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code250() throws UnexpectedCodeException {
        RMD rmd = new RMD(serverDTP, ftp);
        rmd.execute();
        String response = rmd.getResponseMessage();

        assertEquals("250 Requested file action okay\r\n", response);
    }

    @Test
    void execute_NoSuchDir_Code550() throws UnexpectedCodeException {
        RMD rmd = new RMD(serverDTP, ftp + "1");
        rmd.execute();
        String response = rmd.getResponseMessage();

        assertEquals("550 Requested action not taken, file unavailable\r\n", response);
    }

    @AfterEach
    void rmdteFile() {
        file.delete();
    }
}