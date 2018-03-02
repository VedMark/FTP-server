package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import ftp_server.server.ModeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class STRUTest {
    DataTransferProcess serverDTP;

    @BeforeEach
    void initStruTest() {
        serverDTP = new DataTransferProcess();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
    }

    @Test
    void execute_NotAuthorized_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();

        STRU stru = new STRU(serverDTP, "F");
        stru.execute();
        String response = stru.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_ValidParam_Code200() throws UnexpectedCodeException {
        STRU stru = new STRU(serverDTP, "F");
        stru.execute();
        String response = stru.getResponseMessage();
        assertEquals("200 structure: stream\r\n", response);
        assertEquals(ModeEnum.Stream, serverDTP.getParameters().getMode());
    }

    @Test
    void execute_ValidParam3_Code504() throws UnexpectedCodeException {
        STRU stru = new STRU(serverDTP, "P");
        stru.execute();
        String response = stru.getResponseMessage();
        assertEquals("504 Command not implemented for that parameter\r\n", response);
    }
}