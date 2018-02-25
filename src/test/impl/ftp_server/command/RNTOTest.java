package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RNTOTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        RNTO rnto = new RNTO(new FTPServerDTP(), "admin");
        rnto.execute();
        String response = rnto.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}