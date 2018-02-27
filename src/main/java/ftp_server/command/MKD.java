package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.utils.FileSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MKD implements Command {
    private static final String IO_ERROR_MESSAGE = "directory exists or no parent exists";

    private FTPServerDTP receiver;
    Reply reply;
    private String pathname;

    public MKD(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            Path path = Paths.get(FileSystem.getAbsolutePath(receiver.getParameters().getHome(), pathname));
            createDirectory(path);
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
            reply = new Reply(Reply.Code.CODE_257);
        } catch(IOException e) {
            reply = new Reply(Reply.Code.CODE_550);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_257 == this.reply.getReplyCode()) {
            message = getCode257FormattedString();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else if(Reply.Code.CODE_550 == this.reply.getReplyCode()) {
            message = getCode550FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode257FormattedString() {
        return String.format(this.reply.getMessage(), receiver.getParameters().getWorkingDir());
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }

    private String getCode550FormattedString() {
        return String.format(this.reply.getMessage(), IO_ERROR_MESSAGE);
    }
}
