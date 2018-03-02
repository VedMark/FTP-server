package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import ftp_server.server.FormEnum;
import ftp_server.server.TypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TYPETest {
    private DataTransferProcess serverDTP;

    @BeforeEach
    void initTypeTest() {
        serverDTP = new DataTransferProcess();
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
        assertEquals("200 type: ascii\r\n", response);
        assertEquals(TypeEnum.ASCII, serverDTP.getParameters().getType());
    }

    @Test
    void execute_ValidParam2_Code200() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "A N");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("200 type: ascii\r\n", response);
        assertEquals(TypeEnum.ASCII, serverDTP.getParameters().getType());
        assertEquals(FormEnum.NON_PRINT, serverDTP.getParameters().getForm());
    }

    @Test
    void execute_ValidParam3_Code504() throws UnexpectedCodeException {
        TYPE type = new TYPE(serverDTP, "A T");
        type.execute();
        String response = type.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
        assertEquals(TypeEnum.ASCII, serverDTP.getParameters().getType());
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
        assertEquals("200 type: image\r\n", response);
        assertEquals(TypeEnum.IMAGE, serverDTP.getParameters().getType());
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