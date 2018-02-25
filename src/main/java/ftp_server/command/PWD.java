package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class PWD implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public PWD(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_257);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;

        if(Reply.Code.CODE_257 == this.reply.getReplyCode()) {
            message = getCode257FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode257FormattedString() {
        return String.format(this.reply.getMessage(), this.receiver.getParameters().getWorkingDir());
    }
}
