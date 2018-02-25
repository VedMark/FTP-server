package ftp_server.command;

import ftp_server.reply.Reply;

public class BadCommand implements Command {

    public BadCommand() {
    }

    @Override
    public Reply execute() {
        return new Reply(Reply.Code.CODE_500);
    }
}
