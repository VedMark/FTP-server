package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMNTTest {
    @Test
    void execute_Code502() throws UnexpectedCodeException {
        SMNT smnt = new SMNT(new FTPServerDTP(), "admin");
        smnt.execute();
        String response = smnt.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}