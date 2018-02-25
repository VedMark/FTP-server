package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HELPTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        HELP help = new HELP(new FTPServerDTP(), "admin");
        help.execute();
        String response = help.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}