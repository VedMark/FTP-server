package ftp_server.command;

import ftp_server.server.FTPServerDTP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PWDTest {

    @Test
    void execute_ValidTest_Code257() throws UnexpectedCodeException {
        PWD pwd = new PWD(new FTPServerDTP());
        pwd.execute();
        String response = pwd.getResponseMessage();
        assertEquals("257 Your current location is /\r\n", response);
    }
}