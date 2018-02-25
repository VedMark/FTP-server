package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SITETest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        SITE site = new SITE(new FTPServerDTP(), "admin");
        site.execute();
        String response = site.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}