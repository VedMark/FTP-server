package server;

import java.util.Properties;

public class FTPProperties {
    private Integer port;
    private Integer capacity;

    FTPProperties(Properties params) {
        this.port = Integer.parseInt(params.getProperty("ftp.port"));
        this.capacity = Integer.parseInt(params.getProperty("ftp.capacity"));
    }

    public Integer getPort() {
        return port;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
