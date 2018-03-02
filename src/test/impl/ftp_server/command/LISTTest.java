package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import ftp_server.server.ServiceChannelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LISTTest {
    @Test
    void execute_Code530() throws UnexpectedCodeException, ServiceChannelException {
        LIST list = new LIST(new DataTransferProcess(), "admin");
        list.execute();
        String response = list.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_Code202() throws UnexpectedCodeException, ServiceChannelException {
        DataTransferProcess serverDTP = new DataTransferProcess();
        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();

        LIST list = new LIST(serverDTP, "admin");
        list.execute();
        String response = list.getResponseMessage();
        assertEquals("202 Command not implemented, superfluous at this site\r\n", response);
    }
}