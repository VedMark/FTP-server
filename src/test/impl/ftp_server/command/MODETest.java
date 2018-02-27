package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import ftp_server.server.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MODETest {
    FTPServerDTP serverDTP;

    @BeforeEach
    void initModeTest() {
        serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        MODE mode = new MODE(serverDTP, "S");
        mode.execute();
        String response = mode.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_ValidParam_Code200() throws UnexpectedCodeException {
        MODE mode = new MODE(serverDTP, "S");
        mode.execute();
        String response = mode.getResponseMessage();
        assertEquals("200 Command okay, mode: stream\r\n", response);
        assertEquals(Mode.Stream, serverDTP.getParameters().getMode());
    }

    @Test
    void execute_ValidParam3_Code504() throws UnexpectedCodeException {
        MODE mode = new MODE(serverDTP, "B");
        mode.execute();
        String response = mode.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
    }
}