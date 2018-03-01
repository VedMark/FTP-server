package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DELETest {
    DataTransferProcess serverDTP;
    private File file;
    private String ftp;

    @BeforeEach
    void initAccount() {
        serverDTP = new DataTransferProcess();

        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        ftp = "ftp";
        String path = serverDTP.getParameters().getHome() + "/" + ftp;
        file = new File(path);
        try {
            if(!file.createNewFile()) {
                fail("Could not create file");
            }
        } catch (IOException e) {
            fail("Could not create file");
        }
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        DELE dele = new DELE(serverDTP, "ftp");
        dele.execute();
        String response = dele.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_CorrectPath_Code250() throws UnexpectedCodeException {
        DELE dele = new DELE(serverDTP, ftp);
        dele.execute();
        String response = dele.getResponseMessage();

        assertEquals("250 Requested file action okay\r\n", response);
    }

    @Test
    void execute_NotAFile_Code250() throws UnexpectedCodeException {
        file.delete();
        String path = serverDTP.getParameters().getHome() + "/" + ftp;
        file = new File(path);
        if(!file.mkdir()) {
            fail("Could not create file");
        }
        DELE dele = new DELE(serverDTP, ftp);
        dele.execute();
        String response = dele.getResponseMessage();

        assertEquals("550 Requested action not taken, no such file\r\n", response);
    }

    @Test
    void execute_NoPermission_Code450() throws UnexpectedCodeException {
        File f = new File(file.getParent());
        f.setWritable(false);

        DELE dele = new DELE(serverDTP, ftp);
        dele.execute();
        String response = dele.getResponseMessage();

        f.setWritable(true);

        assertEquals("450 Requested file action not taken, you have no permission for the action\r\n", response);
    }

    @Test
    void execute_NoSuchFile_Code550() throws UnexpectedCodeException {
        DELE dele = new DELE(serverDTP, ftp + "1");
        dele.execute();
        String response = dele.getResponseMessage();

        assertEquals("550 Requested action not taken, no such file\r\n", response);
    }

    @AfterEach
    void deleteFile() {
        file.delete();
    }
}