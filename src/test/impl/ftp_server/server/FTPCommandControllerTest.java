package ftp_server.server;

import ftp_server.ApplicationTests;
import ftp_server.command.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FTPCommandControllerTest extends ApplicationTests {
    private FTPCommandController controller = new FTPCommandController(new FTPServerDTP());

    @Test
    void createCommand_USER() {
        Command command = this.controller.createCommand("USER admin");
        assertTrue(command instanceof USER);
    }

    @Test
    void createCommand_PASS() {
        Command command = this.controller.createCommand("PASS");
        assertTrue(command instanceof PASS);
    }


    @Test
    void createCommand_QUIT() {
        Command command = this.controller.createCommand("QUIT");
        assertTrue(command instanceof QUIT);
    }


    @Test
    void createCommand_SYST() {
        Command command = this.controller.createCommand("SYST");
        assertTrue(command instanceof SYST);
    }


    @Test
    void createCommand_BadCommand() {
        Command command = this.controller.createCommand("");
        assertTrue(command instanceof BadCommand);
    }
}