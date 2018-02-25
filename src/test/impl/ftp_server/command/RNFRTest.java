package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RNFRTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        RNFR rnfr = new RNFR(new FTPServerDTP(), "admin");
        rnfr.execute();
        String response = rnfr.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}