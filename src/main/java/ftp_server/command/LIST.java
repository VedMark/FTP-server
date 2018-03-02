package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.server.ServiceChannelException;
import ftp_server.utils.FileSystem;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LIST implements Command {
    private DataTransferProcess receiver;
    Reply reply;
    private String pathname;

    public LIST(DataTransferProcess serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname.trim();
        if(!pathname.isEmpty()) {
            this.pathname = isAbsolutePath(pathname) ?
                    receiver.getParameters().getHome() + pathname :
                    receiver.getParameters().getHome() + "/" + pathname;
        }
    }

    @Override
    public void execute() throws ServiceChannelException {
        if(receiver.getParameters().isAuthorized()) {
            if (pathname.isEmpty()) {
                pathname = ".";
            }

            receiver.start();
            receiver.sendListInfo(Paths.get(pathname));
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private boolean isAbsolutePath(String pathname) {
        return !pathname.isEmpty() && '/' == pathname.charAt(0);
    }

    private String getCode212FormattedString() {
        return String.format(this.reply.getMessage(), FileSystem.dirInfo(receiver.getParameters().getHome(), pathname));
    }

    private String getCode213FormattedString() {
        return String.format(this.reply.getMessage(), FileSystem.fileInfo(pathname));
    }

    private String getCode450FormattedString() {
        return String.format(this.reply.getMessage(), "file does not exists");
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
