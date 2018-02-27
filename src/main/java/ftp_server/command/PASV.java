package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

import java.net.InetSocketAddress;

public class PASV implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public PASV(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            receiver.getParameters().toPassiveProcess();
            reply = new Reply(Reply.Code.CODE_227);
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_227 == this.reply.getReplyCode()) {
            message = getCode227FormattedString();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode227FormattedString() {
        InetSocketAddress serverAddress = receiver.getParameters().getServerAddress();
        String[] arr = serverAddress.getAddress().getHostAddress().split("\\.");
        String el4 = String.valueOf(serverAddress.getPort() / 256);
        String el5 = String.valueOf(serverAddress.getPort() % 256);

        return String.format(this.reply.getMessage(), arr[0], arr[1], arr[2], arr[3], el4, el5);
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}

