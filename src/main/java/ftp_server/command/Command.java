package ftp_server.command;

import ftp_server.reply.Reply;

public interface Command {
    void execute();
    String getResponseMessage() throws UnexpectedCodeException;
}
