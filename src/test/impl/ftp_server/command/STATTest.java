package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class STATTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        STAT stat = new STAT(new FTPServerDTP(), "admin");
        stat.execute();
        String response = stat.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}