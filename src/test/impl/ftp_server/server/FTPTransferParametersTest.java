package ftp_server.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FTPTransferParametersTest {
    private FTPTransferParameters parameters;

    @BeforeEach
    void initFTPTransferParameters() {
        parameters = new FTPTransferParameters();
    }

    @Test
    void getUsername() {
        assertEquals("", parameters.getUsername());
    }

    @Test
    void setUsername() {
        final String string = "admin";
        parameters.setUsername(string);
        assertEquals(string, parameters.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("", parameters.getPassword());
    }

    @Test
    void setPassword() {
        final String string = "password";
        parameters.setPassword(string);
        assertEquals(string, parameters.getPassword());
    }

    @Test
    void getHome() {
        assertEquals("", parameters.getHome());
    }

    @Test
    void setHome() {
        final String string = "/home/root";
        parameters.setHome(string);
        assertEquals(string, parameters.getHome());
    }

    @Test
    void getType() {
        assertEquals(Type.ASCII, parameters.getType());
    }

    @Test
    void setType() {
        final Type type = Type.BINARY;
        parameters.setType(type);
        assertEquals(type, parameters.getType());
    }

    @Test
    void getMode() {
        assertEquals(Mode.Stream, parameters.getMode());
    }

    @Test
    void setMode() {
        final Mode mode = Mode.Block;
        parameters.setMode(mode);
        assertEquals(mode, parameters.getMode());
    }

    @Test
    void getStructure() {
        assertEquals(Structure.FILE, parameters.getStructure());
    }

    @Test
    void setStructure() {
        final Structure structure = Structure.RECORD;
        parameters.setStructure(structure);
        assertEquals(structure, parameters.getStructure());
    }

    @Test
    void isAuthorized() {
        assertEquals(false, parameters.isAuthorized());
    }

    @Test
    void setAuthorized() {
        parameters.setAuthorized(true);
        assertEquals(true, parameters.isAuthorized());
    }

    @Test
    void getWorkingDir() {
        assertEquals("/", parameters.getWorkingDir());
    }

    @Test
    void setWorkingDir() {
        final String string = "/home/root";
        parameters.setWorkingDir(string);
        assertEquals(string, parameters.getWorkingDir());
    }

    @Test
    void reset() {
        parameters.reset();
        assertEquals("", parameters.getUsername());
        assertEquals("", parameters.getPassword());
        assertEquals("", parameters.getHome());
        assertEquals("/", parameters.getWorkingDir());
        assertEquals(Type.ASCII, parameters.getType());
        assertEquals(Mode.Stream, parameters.getMode());
        assertEquals(Structure.FILE, parameters.getStructure());
        assertEquals(false, parameters.isAuthorized());
    }
}