package ftp_server.command;

import ftp_server.reply.Reply;

public class QUIT implements Command {
    Reply reply;

    public QUIT() {
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_221);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;

        if(Reply.Code.CODE_221 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }
}
