package ftp_server.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServerPropertiesTest {

    @Test
    void getPortPI() throws ConfigException {
        assertNotNull(ServerProperties.getPortPI());
    }

    @Test
    void getPortDTP() throws ConfigException {
        assertNotNull(ServerProperties.getPortDTP());
    }

    @Test
    void getCapacity() throws ConfigException {
        assertNotNull(ServerProperties.getCapacity());
    }

    @Test
    void getTimeout() throws ConfigException {
        assertNotNull(ServerProperties.getTimeout());
    }

    @Test
    void getHome() {
        assertNotNull(ServerProperties.getHome("root"));
    }

    @Test
    void getPassword() {
        assertNotNull(ServerProperties.getPassword("root"));
    }
}