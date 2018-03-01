package ftp_server.command;

import ftp_server.server.ServiceChannelException;

public interface Command {
    String NOT_AUTHENTICATED_MESSAGE = "You are not logged in";

    void execute() throws ServiceChannelException;
    String getResponseMessage() throws UnexpectedCodeException;
}
