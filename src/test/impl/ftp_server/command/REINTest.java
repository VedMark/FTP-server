package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class REINTest {

    @Test
    void execute() throws UnexpectedCodeException {
        REIN rein = new REIN(new DataTransferProcess());
        rein.execute();
        String response = rein.getResponseMessage();
        assertEquals("220-----------Welcome to FTP-server----------\n" +
                "220-You will be disconnected after 10 minutes of inactivity\n" +
                "220 Service ready\r\n",
                response);
    }
}