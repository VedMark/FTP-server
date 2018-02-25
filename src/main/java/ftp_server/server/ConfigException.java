package ftp_server.server;

public class ConfigException extends Exception {
    ConfigException(String message) {
        super(message, null, false, false);
    }
}
