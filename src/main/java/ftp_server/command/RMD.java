package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.utils.FileSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RMD implements Command {
    private static final String NO_SUCH_DIR_MESSAGE = "file unavailable";

    private FTPServerDTP receiver;
    Reply reply;
    private String pathname;

    public RMD(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            deleteFile(FileSystem.getAbsolutePath(receiver.getParameters().getHome(), pathname));
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private void deleteFile(String filePath) {
        try {
            if(Files.isDirectory(Paths.get(filePath))) {
                Files.delete(Paths.get(filePath));
                reply = new Reply(Reply.Code.CODE_250);
            } else {
                reply = new Reply(Reply.Code.CODE_550);
            }
        } catch (IOException exception) {
            reply = new Reply(Reply.Code.CODE_550);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_250 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else if(Reply.Code.CODE_550 == this.reply.getReplyCode()) {
            message = getCode550FormattedString();
        }else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }

    private String getCode550FormattedString() {
        return String.format(this.reply.getMessage(), NO_SUCH_DIR_MESSAGE);
    }
}
