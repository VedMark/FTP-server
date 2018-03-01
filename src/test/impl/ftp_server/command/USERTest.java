package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USERTest {
    private DataTransferProcess dtp;

    @BeforeEach
    void initServerDTP() {
        dtp = new DataTransferProcess();
    }

    @Test
    void execute_ValidUserWithPassword_Code_331() throws UnexpectedCodeException {
        USER user = new USER(dtp, "admin");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("331 User admin okay, need password\r\n", response);
        assertEquals(false, dtp.getParameters().isAuthorized());
        assertEquals("", dtp.getParameters().getHome());
    }

    @Test
    void execute_ValidUserWithNoPassword_Code_331() throws UnexpectedCodeException {
        USER user = new USER(dtp, "mark");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("230 User logged in, proceed\r\n", response);
        assertEquals(true, dtp.getParameters().isAuthorized());
        assertEquals("/home/mark", dtp.getParameters().getHome());
    }

    @Test
    void execute_NotValidName_Code_501() throws UnexpectedCodeException {
        USER user = new USER(dtp, "adm\rin");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("501 Syntax error in parameters or arguments\r\n", response);
        assertEquals(false, dtp.getParameters().isAuthorized());
        assertEquals("", dtp.getParameters().getHome());
    }

    @Test
    void execute_NotRegisteredUser_Code_501() throws UnexpectedCodeException {
        USER user = new USER(dtp, "qwerty");
        user.execute();
        String response = user.getResponseMessage();
        assertEquals("331 User qwerty okay, need password\r\n", response);
        assertEquals(false, dtp.getParameters().isAuthorized());
        assertEquals("", dtp.getParameters().getHome());
    }

    @Test
    void execute_AlreadyLoggedIn_Code_530() throws UnexpectedCodeException {
        USER user = new USER(dtp, "mark");
        user.execute();
        user = new USER(dtp, "admin");
        user.execute();

        String response = user.getResponseMessage();
        assertEquals("530 You are already logged in\r\n", response);
        assertEquals(true, dtp.getParameters().isAuthorized());
        assertEquals("/home/mark", dtp.getParameters().getHome());
    }
}