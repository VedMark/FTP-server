package ftp_server.command;

import ftp_server.reply.Reply;

public class NOOP implements Command {
    Reply reply;

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_200);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return getCode200FormattedString();
    }

    private String getCode200FormattedString() {
        return String.format(this.reply.getMessage(), "Service ready");
    }
}
