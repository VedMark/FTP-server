package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RNFRTest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        RNFR rnfr = new RNFR(new DataTransferProcess(), "admin");
        rnfr.execute();
        String response = rnfr.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        RNFR rnfr = new RNFR(serverDTP, "admin");
        rnfr.execute();
        String response = rnfr.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}