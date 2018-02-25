package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class STOUTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        STOU stou = new STOU(new FTPServerDTP());
        stou.execute();
        String response = stou.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}