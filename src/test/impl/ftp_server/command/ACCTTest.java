package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ACCTTest {

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        ACCT acct = new ACCT(new FTPServerDTP(), "admin");
        acct.execute();
        String response = acct.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}