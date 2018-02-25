package ftp_server.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadCommandTest {

    @Test
    void execute_Code500() throws UnexpectedCodeException {
        BadCommand badCommand = new BadCommand();
        badCommand.execute();
        String response = badCommand.getResponseMessage();
        assertEquals("500 Syntax error, command unrecognized\r\n", response);
    }
}