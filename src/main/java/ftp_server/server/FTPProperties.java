package ftp_server.server;

import java.util.ResourceBundle;

public class FTPProperties {
    private ResourceBundle properties;

    FTPProperties(ResourceBundle properties) {
        this.properties = properties;
    }

    public Integer getPortPI() throws ConfigException {
        String portPI = this.properties.getString("ftp.portPI");
        if (portPI.contains("\\D")) {
            throw new ConfigException("portPI is not a number");
        }
        return Integer.parseInt(portPI);
    }

    public Integer getPortDTP() throws ConfigException{
        String portDTP = this.properties.getString("ftp.portDTP");
        if (portDTP.contains("\\D")) {
            throw new ConfigException("portDTP is not a number");
        }
        return Integer.parseInt(portDTP);
    }

    public Integer getCapacity() throws ConfigException {
        String capacity = this.properties.getString("ftp.capacity");
        if (capacity.contains("\\D")) {
            throw new ConfigException("capacity is not a number");
        }
        return Integer.parseInt(capacity);
    }

    public String getHome(String user) throws ConfigException {
        String home = this.properties.getString("ftp.home." + user);
        if (home.contains("\\W")) {
            throw new ConfigException("ftp.home." + user + " is not a valid string");
        }
        return home;
    }

    public String getPassword(String user) throws ConfigException {
        String password = this.properties.getString("ftp.user." + user);
        if (password.contains("\\W")) {
            throw new ConfigException("ftp.user." + user + " is not a valid string");
        }
        return password;
    }
}
