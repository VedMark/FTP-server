package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NLSTTest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        NLST nlst = new NLST(new FTPServerDTP(), "admin");
        nlst.execute();
        String response = nlst.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}