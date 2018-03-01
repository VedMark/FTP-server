package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ALLOTest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        ALLO acct = new ALLO(new DataTransferProcess(), "admin");
        acct.execute();
        String response = acct.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        ALLO allo = new ALLO(serverDTP, "admin");
        allo.execute();
        String response = allo.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}