package server;

import java.util.Properties;

public class FTPProperties {
    private Properties properties;

    FTPProperties(Properties params) {
        this.properties = params;
    }

    public Integer getPortPI() {
        return Integer.parseInt(this.properties.getProperty("ftp.portPI"));
    }

    public Integer getPortDTP() {
        return Integer.parseInt(this.properties.getProperty("ftp.portDTP"));
    }

    public Integer getCapacity() {
        return Integer.parseInt(this.properties.getProperty("ftp.capacity"));
    }

    public String getHome(String user) {
        return this.properties.getProperty("ftp.home." + user);
    }

    public String getPassword(String user) {
        return this.properties.getProperty("ftp.user." + user);
    }
}
