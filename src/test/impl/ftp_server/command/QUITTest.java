package ftp_server.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QUITTest {

    @Test
    void execute() throws UnexpectedCodeException {
        QUIT quit = new QUIT();
        quit.execute();
        String response = quit.getResponseMessage();
        assertEquals("221-Goodbye\n221 Logout\r\n", response);
    }
}