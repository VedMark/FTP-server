package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMNTTest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        SMNT smnt = new SMNT(new FTPServerDTP(), "admin");
        smnt.execute();
        String response = smnt.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        SMNT smnt = new SMNT(serverDTP, "admin");
        smnt.execute();
        String response = smnt.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}