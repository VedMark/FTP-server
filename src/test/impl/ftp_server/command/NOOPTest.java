package ftp_server.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NOOPTest {

    @Test
    void execute_Okay_Code200() throws UnexpectedCodeException {
        NOOP noop = new NOOP();
        noop.execute();

        String response = noop.getResponseMessage();
        assertEquals("200 Service ready\r\n", response);
    }
}