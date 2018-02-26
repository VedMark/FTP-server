package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RNTOTest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        RNTO rnto = new RNTO(new FTPServerDTP(), "admin");
        rnto.execute();
        String response = rnto.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        FTPServerDTP serverDTP = new FTPServerDTP();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        RNTO rnto = new RNTO(serverDTP, "admin");
        rnto.execute();
        String response = rnto.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}