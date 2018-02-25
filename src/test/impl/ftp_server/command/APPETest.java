package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class APPETest {

    @Test
    void execute_Code502() throws UnexpectedCodeException {
        APPE appe = new APPE(new FTPServerDTP(), "admin");
        appe.execute();
        String response = appe.getResponseMessage();
        assertEquals("502 Command not implemented\r\n", response);
    }
}