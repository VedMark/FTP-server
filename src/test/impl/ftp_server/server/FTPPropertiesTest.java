package ftp_server.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FTPPropertiesTest {

    @Test
    void getPortPI() throws ConfigException {
        assertEquals(new Short((short) 21), FTPProperties.getPortPI());
    }

    @Test
    void getPortDTP() throws ConfigException {
        assertEquals(new Short((short) 20), FTPProperties.getPortDTP());
    }

    @Test
    void getCapacity() throws ConfigException {
        assertEquals((Integer) 5, FTPProperties.getCapacity());
    }

    @Test
    void getTimeout() throws ConfigException {
        assertEquals((Integer) 600000, FTPProperties.getTimeout());
    }

    @Test
    void getHome() {
        assertEquals("/home/root", FTPProperties.getHome("root"));
    }

    @Test
    void getPassword() {
        assertEquals("123456", FTPProperties.getPassword("root"));
    }
}