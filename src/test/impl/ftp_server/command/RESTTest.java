package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RESTTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        REST rest = new REST(new FTPServerDTP(), "admin");
        rest.execute();
        String response = rest.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}