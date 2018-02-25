package ftp_server.command;

import ftp_server.reply.Reply;

public class BadCommand implements Command {
    Reply reply;

    public BadCommand() {
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_500);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_500 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }
}
