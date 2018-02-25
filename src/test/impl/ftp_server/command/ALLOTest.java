package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ALLOTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        ALLO allo = new ALLO(new FTPServerDTP(), "admin");
        allo.execute();
        String response = allo.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}