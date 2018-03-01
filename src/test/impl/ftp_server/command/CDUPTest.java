package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CDUPTest {
    private DataTransferProcess serverDTP;

    @BeforeEach
    void initAccount() {
        serverDTP = new DataTransferProcess();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        CDUP cdup = new CDUP(serverDTP);
        cdup.execute();
        String response = cdup.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code200() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
        String ftp = "ftp";
        String path = serverDTP.getParameters().getHome() + ftp;
        File dir = new File(path);
        dir.mkdir();

        CDUP cdup = new CDUP(serverDTP);
        cdup.execute();
        String response = cdup.getResponseMessage();
        assertEquals("200 Command okay, current directory is /\r\n", response);
        assertEquals("/", serverDTP.getParameters().getWorkingDir());

        dir.delete();
    }

    @Test
    void execute_PathBeyondAllowed_Code200() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        CDUP cdup = new CDUP(serverDTP);
        cdup.execute();
        String response = cdup.getResponseMessage();
        assertEquals("200 Command okay, current directory is /\r\n", response);
        assertEquals("/", serverDTP.getParameters().getWorkingDir());
    }
}