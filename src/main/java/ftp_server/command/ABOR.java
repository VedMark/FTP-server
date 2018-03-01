package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.server.ServiceChannelException;

public class ABOR implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public ABOR(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() throws ServiceChannelException {
        if (!receiver.isConnectionOpen()) {
            reply = new Reply(Reply.Code.CODE_226);
        } else {
            receiver.abortConnection();
            reply = new Reply(Reply.Code.CODE_426);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_226 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_426 == this.reply.getReplyCode()) {
            message = reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }
}
