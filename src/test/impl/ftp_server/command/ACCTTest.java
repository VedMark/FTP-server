package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ACCTTest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        ACCT acct = new ACCT(new FTPServerDTP(), "admin");
        acct.execute();
        String response = acct.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        ACCT acct = new ACCT(serverDTP, "admin");
        acct.execute();
        String response = acct.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}