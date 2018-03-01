package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.server.ServiceChannelException;

import java.net.InetSocketAddress;

public class PASV implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public PASV(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() throws ServiceChannelException {
        if(this.receiver.getParameters().isAuthorized()) {
            receiver.getParameters().toPassiveProcess();
            reply = new Reply(Reply.Code.CODE_150);
            receiver.start();
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_150 == this.reply.getReplyCode()) {
            message = getCode150FormattedString();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode150FormattedString() {
        String message =  "Accepted data connection";
        return String.format(reply.getMessage(), message);
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}

