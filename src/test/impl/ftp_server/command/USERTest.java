package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USERTest {

    @Test
    void execute_ValidUserWithPassword_Code_331() throws UnexpectedCodeException {
        USER user = new USER(new FTPServerDTP(), "root");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("331 User root okay, need password\r\n", response);
    }

    @Test
    void execute_ValidUserWithNoPassword_Code_331() throws UnexpectedCodeException {
        USER user = new USER(new FTPServerDTP(), "mark");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("230 User logged in, proceed\r\n", response);
    }

    @Test
    void execute_NotValidName_Code_501() throws UnexpectedCodeException {
        USER user = new USER(new FTPServerDTP(), "ro\rot");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("501 Syntax error in parameters or arguments\r\n", response);
    }

    @Test
    void execute_NotRegisteredUser_Code_501() throws UnexpectedCodeException {
        USER user = new USER(new FTPServerDTP(), "qwerty");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("331 User qwerty okay, need password\r\n", response);
    }
}