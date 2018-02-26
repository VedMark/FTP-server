package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.utils.FileSystem;

import java.io.File;
import java.io.IOException;

public class CDUP implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public CDUP(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            receiver.getParameters().setWorkingDir(FileSystem.normalizePath(receiver.getParameters().getHome(), ".."));
            reply = new Reply(Reply.Code.CODE_200);

        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_200 == this.reply.getReplyCode()) {
            message = getCode200FormattedString();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode200FormattedString() {
        return String.format(
                this.reply.getMessage(),
                "current directory is " + receiver.getParameters().getWorkingDir()
        );
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
