package ftp_server.command;

import ftp_server.Main;
import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.server.ServiceChannelException;
import ftp_server.utils.FileSystem;
import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RETR implements Command {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private DataTransferProcess receiver;
    Reply reply;
    private String pathname;

    public RETR(DataTransferProcess serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() throws ServiceChannelException {
        if(!receiver.getParameters().isAuthorized()) {
            reply = new Reply(Reply.Code.CODE_530);
        } else {
            Path path = Paths.get(FileSystem.getAbsolutePath(receiver.getParameters().getHome(), pathname));
            if(Files.notExists(path)) {
                reply = new Reply(Reply.Code.CODE_550);
            } else if(Files.isDirectory(path)) {
                reply = new Reply(Reply.Code.CODE_450);
            } else if(Files.isRegularFile(path)) {
                receiver.start();
                receiver.retrieveFile(path);
            }
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message = null;
        if(reply != null) {
            if (Reply.Code.CODE_450 == this.reply.getReplyCode()) {
                message = getCode450FormattedString();
            } else if (Reply.Code.CODE_451 == this.reply.getReplyCode()) {
                message = reply.getMessage();
            } else if (Reply.Code.CODE_530 == this.reply.getReplyCode()) {
                message = getCode530FormattedString();
            } else if (Reply.Code.CODE_550 == this.reply.getReplyCode()) {
                message = getCode550FormattedString();
            } else {
                throw new UnexpectedCodeException();
            }
        }

        return message;
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }

    private String getCode450FormattedString() {
        return String.format(this.reply.getMessage(), "file does not exists");
    }

    private String getCode550FormattedString() {
        return String.format(this.reply.getMessage(), "can be retrieved only regular files");
    }
}
