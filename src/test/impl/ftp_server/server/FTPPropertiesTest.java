package ftp_server.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FTPPropertiesTest {

    @Test
    void getPortPI() throws ConfigException {
        assertNotNull(FTPProperties.getPortPI());
    }

    @Test
    void getPortDTP() throws ConfigException {
        assertNotNull(FTPProperties.getPortDTP());
    }

    @Test
    void getCapacity() throws ConfigException {
        assertNotNull(FTPProperties.getCapacity());
    }

    @Test
    void getTimeout() throws ConfigException {
        assertNotNull(FTPProperties.getTimeout());
    }

    @Test
    void getHome() {
        assertNotNull(FTPProperties.getHome("root"));
    }

    @Test
    void getPassword() {
        assertNotNull(FTPProperties.getPassword("root"));
    }
}