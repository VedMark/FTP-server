package ftp_server.command;

public interface Command {
    String NOT_AUTHENTICATED_MESSAGE = "You are not logged in";

    void execute();
    String getResponseMessage() throws UnexpectedCodeException;
}
