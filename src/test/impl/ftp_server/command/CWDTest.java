package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CWDTest {
    private FTPServerDTP serverDTP;

    @BeforeEach
    void initAccount() {
        serverDTP = new FTPServerDTP();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        CWD cwd = new CWD(serverDTP, "ftp");
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code250() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
        String ftp = "ftp";
        String path = serverDTP.getParameters().getHome() + ftp;
        File dir = new File(path);
        dir.mkdir();

        CWD cwd = new CWD(serverDTP, ftp);
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/ftp/", serverDTP.getParameters().getWorkingDir());

        dir.delete();
    }

    @Test
    void execute_CorrectDifficultPath_Code250() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
        String ftp = "/ftp";
        String dir = "/dir";
        String ftp_to_check = "ftp/../ftp/dir";
        String path = serverDTP.getParameters().getHome();
        File d = new File(path + ftp + dir);
        d.mkdir();

        CWD cwd = new CWD(serverDTP, ftp_to_check);
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/ftp/dir/", serverDTP.getParameters().getWorkingDir());

        d.delete();
    }

    @Test
    void execute_CorrectPathBeyondAllowed_Code250() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        CWD cwd = new CWD(serverDTP, "..");
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/", serverDTP.getParameters().getWorkingDir());
    }

    @Test
    void execute_IncorrectPath_Code550() throws UnexpectedCodeException {
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        CWD cwd = new CWD(serverDTP, "http");
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("550 Requested action not taken, no such directory\r\n", response);
    }
}