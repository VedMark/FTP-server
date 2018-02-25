package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PASSTest {

    @Test
    void execute_PasswordBeforeUser_Code503() throws UnexpectedCodeException {
        PASS pass = new PASS(new FTPServerDTP(), "123456");
        pass.execute();
        String response = pass.getResponseMessage();
        assertEquals("503 Bad sequence of commands\r\n", response);
    }

    @Test
    void execute_ValidPassword_Code230() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "root");
        PASS pass = new PASS(serverDTP, "123456");
        user.execute();
        pass.execute();
        String response = pass.getResponseMessage();
        assertEquals("230 User logged in, proceed\r\n", response);
    }

    @Test
    void execute_InValidPassword_Code530() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "root");
        PASS pass = new PASS(serverDTP, "123");
        user.execute();
        pass.execute();
        String response = pass.getResponseMessage();
        assertEquals("530 Not logged in\r\n", response);
    }
}