package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.utils.FileSystem;

public class CDUP implements Command {
    private DataTransferProcess receiver;
    Reply reply;

    public CDUP(DataTransferProcess serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            receiver.getParameters().setWorkingDir(FileSystem.normalizeDirPath(receiver.getParameters().getHome(), ".."));
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
                "Current directory is " + receiver.getParameters().getWorkingDir()
        );
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
