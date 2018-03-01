package ftp_server.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class TransferParametersTest {
    private TransferParameters parameters;

    @BeforeEach
    void initFTPTransferParameters() {
        parameters = new TransferParameters();
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
    void getUserAddress() {
        assertNull(parameters.getUserAddress());
    }

    @Test
    void getServerAddress() {
        assertNull(parameters.getServerAddress());
    }

    @Test
    void setServerAddress() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 800);
        parameters.setServerAddress(address);
        assertEquals(address, parameters.getServerAddress());
    }

    @Test
    void toActiveProcess() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 800);
        parameters.toActiveProcess(address);
        assertEquals(address, parameters.getUserAddress());
    }

    @Test
    void isPassiveProcess() {
        assertEquals(false, parameters.isPassiveProcess());
    }

    @Test
    void getType() {
        assertEquals(TypeEnum.ASCII, parameters.getType());
    }

    @Test
    void setType() {
        final TypeEnum type = TypeEnum.IMAGE;
        parameters.setType(type);
        assertEquals(type, parameters.getType());
    }

    @Test
    void getMode() {
        assertEquals(ModeEnum.Stream, parameters.getMode());
    }

    @Test
    void setMode() {
        final ModeEnum mode = ModeEnum.Block;
        parameters.setMode(mode);
        assertEquals(mode, parameters.getMode());
    }

    @Test
    void getForm() {
        assertEquals(FormEnum.NON_PRINT, parameters.getForm());
    }

    @Test
    void setForm() {
        final FormEnum form = FormEnum.NON_PRINT;
        parameters.setForm(form);
        assertEquals(form, parameters.getForm());
    }

    @Test
    void getStructure() {
        assertEquals(StructureEnum.FILE, parameters.getStructure());
    }

    @Test
    void setStructure() {
        final StructureEnum structure = StructureEnum.RECORD;
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
    void reset() {
        parameters.reset();
        assertEquals("", parameters.getUsername());
        assertEquals("", parameters.getPassword());
        assertEquals("", parameters.getHome());
        assertEquals("/", parameters.getWorkingDir());
        assertNull(parameters.getUserAddress());
        assertEquals(true, parameters.isPassiveProcess());
        assertEquals(TypeEnum.ASCII, parameters.getType());
        assertEquals(ModeEnum.Stream, parameters.getMode());
        assertEquals(FormEnum.NON_PRINT, parameters.getForm());
        assertEquals(StructureEnum.FILE, parameters.getStructure());
        assertEquals(false, parameters.isAuthorized());
    }
}