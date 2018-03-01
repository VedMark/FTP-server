package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.utils.FileSystem;

import java.nio.file.Path;
import java.nio.file.Paths;

public class STOR implements Command {

    private DataTransferProcess receiver;
    Reply reply;
    private String pathname;

    public STOR(DataTransferProcess serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {
        if(!receiver.getParameters().isAuthorized()) {
            reply = new Reply(Reply.Code.CODE_530);
        } else {
            Path path = Paths.get(FileSystem.getAbsolutePath(receiver.getParameters().getHome(), pathname));
            receiver.receiveFile(path);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message = null;
        if(reply != null) {
            if (Reply.Code.CODE_530 == this.reply.getReplyCode()) {
                message = getCode530FormattedString();
            } else {
                throw new UnexpectedCodeException();
            }
        }

        return message;
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }

    private String getCode553FormattedString() {
        return String.format(this.reply.getMessage(), "is a directory");
    }
}
