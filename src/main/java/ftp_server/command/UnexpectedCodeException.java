package ftp_server.command;

import java.rmi.UnexpectedException;

public class UnexpectedCodeException extends UnexpectedException {
    public UnexpectedCodeException() {
        super("An unexpected response code for the command");
    }
}
