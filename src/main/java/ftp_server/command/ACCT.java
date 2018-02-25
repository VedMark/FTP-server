package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class ACCT implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String accInfo;

    public ACCT(FTPServerDTP serverDTP, String accInfo) {
        this.receiver = serverDTP;
        this.accInfo = accInfo;
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_202);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_202 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }
}
