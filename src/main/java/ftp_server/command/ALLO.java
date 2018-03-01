package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;

public class ALLO implements Command {
    private DataTransferProcess receiver;
    Reply reply;
    private String dec_int;

    public ALLO(DataTransferProcess serverDTP, String dec_int) {
        this.receiver = serverDTP;
        this.dec_int = dec_int;
    }

    @Override
    public void execute() {
        if(!receiver.getParameters().isAuthorized()) {
            reply = new Reply(Reply.Code.CODE_530);
        } else {
            reply = new Reply(Reply.Code.CODE_202);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_202 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
