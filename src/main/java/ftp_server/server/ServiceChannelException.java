package ftp_server.server;

public class ServiceChannelException extends Exception {
    ServiceChannelException(String message) {
        super(message, null, false, false);
    }
}
