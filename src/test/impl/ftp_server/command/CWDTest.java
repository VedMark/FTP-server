package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CWDTest {
    private DataTransferProcess serverDTP;

    @BeforeEach
    void initAccount() {
        serverDTP = new DataTransferProcess();

        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        CWD cwd = new CWD(serverDTP, "ftp");
        cwd.execute();
        String response = cwd.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code250() throws UnexpectedCodeException {
        String ftp = "ftp1";
        String path = serverDTP.getParameters().getHome() + "/" + ftp;
        File file = new File(path);
        if(!file.mkdir()) {
            fail("Could not create file");
        }

        CWD cwd = new CWD(serverDTP, ftp);
        cwd.execute();
        String response = cwd.getResponseMessage();

        file.delete();
        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/ftp1/", serverDTP.getParameters().getWorkingDir());
    }

    @Test
    void execute_CorrectDifficultPath_Code250() throws UnexpectedCodeException {
        String ftp = "/ftp2";
        String dir = "/dir";
        String ftp_to_check = "ftp2/../ftp2/dir";
        String path = serverDTP.getParameters().getHome();
        File file1 = new File(path + ftp);
        if(!file1.mkdir()) {
            fail("Could not create file");
        }
        File file2 = new File(path + ftp + dir);
        if(!file2.mkdir()) {
            fail("Could not create file");
        }

        CWD cwd = new CWD(serverDTP, ftp_to_check);
        cwd.execute();
        String response = cwd.getResponseMessage();
        file2.delete();
        file1.delete();

        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/ftp2/dir/", serverDTP.getParameters().getWorkingDir());
    }

    @Test
    void execute_CorrectPathBeyondAllowed_Code250() throws UnexpectedCodeException {
        File file = new File("");
        CWD cwd = new CWD(serverDTP, "..");
        cwd.execute();
        String response = cwd.getResponseMessage();
        file.delete();

        assertEquals("250 Requested file action okay\r\n", response);
        assertEquals("/", serverDTP.getParameters().getWorkingDir());
    }

    @Test
    void execute_IncorrectPath_Code550() throws UnexpectedCodeException {
        File file = new File("");
        CWD cwd = new CWD(serverDTP, "http");
        cwd.execute();
        String response = cwd.getResponseMessage();
        file.delete();

        assertEquals("550 Requested action not taken, no such directory\r\n", response);
    }
}