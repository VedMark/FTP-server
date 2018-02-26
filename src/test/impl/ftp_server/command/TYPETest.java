package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import ftp_server.server.Form;
import ftp_server.server.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TYPETest {
    private FTPServerDTP serverDTP;

    @BeforeEach
    void initTypeTest() {
        serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        TYPE type = new TYPE(serverDTP, "A N");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_ValidParam1_Code200() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "A");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("200 Command okay, type: ascii\r\n", response);
        assertEquals(Type.ASCII, serverDTP.getParameters().getType());
    }

    @Test
    void execute_ValidParam2_Code200() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "A N");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("200 Command okay, type: ascii\r\n", response);
        assertEquals(Type.ASCII, serverDTP.getParameters().getType());
        assertEquals(Form.NON_PRINT, serverDTP.getParameters().getForm());
    }

    @Test
    void execute_ValidParam3_Code504() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "A T");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
        assertEquals(Type.ASCII, serverDTP.getParameters().getType());
    }

    @Test
    void execute_ValidParam4_Code200() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "E");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
    }

    @Test
    void execute_ValidParam5_Code200() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "I");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("200 Command okay, type: image\r\n", response);
        assertEquals(Type.IMAGE, serverDTP.getParameters().getType());
    }

    @Test
    void execute_ValidParam6_Code504() throws UnexpectedCodeException {
        TYPE quit = new TYPE(serverDTP, "L 256");
        quit.execute();
        String response = quit.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
    }

    @Test
    void execute_InValidParam_Code501() throws UnexpectedCodeException {
        TYPE quit = new TYPE(serverDTP, "L 257");
        quit.execute();
        String response = quit.getResponseMessage();
        assertEquals("501 Syntax error in parameters or arguments\r\n", response);
    }
}