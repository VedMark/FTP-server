package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SITETest {

    @Test
    void execute_Code530() throws UnexpectedCodeException {
        SITE site = new SITE(new DataTransferProcess(), "admin");
        site.execute();
        String response = site.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        SITE site = new SITE(serverDTP, "admin");
        site.execute();
        String response = site.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}