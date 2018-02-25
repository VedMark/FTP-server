package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class ALLO implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String dec_int;

    public ALLO(FTPServerDTP serverDTP, String dec_int) {
        this.receiver = serverDTP;
        this.dec_int = dec_int;
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_502);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_502 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }
}
