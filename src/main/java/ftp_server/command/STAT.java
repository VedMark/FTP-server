package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.utils.FileSystem;

import java.io.File;

public class STAT implements Command {
    private static final String RESPONSE_211_FORMAT = "mode: %s; type: %s; form: %s; structure: %s";

    private DataTransferProcess receiver;
    Reply reply;
    private String pathname;

    public STAT(DataTransferProcess serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname.trim();
        if(!pathname.isEmpty()) {
            this.pathname = isAbsolutePath(pathname) ? pathname : receiver.getParameters().getHome() + "/" + pathname;
        }
    }

    @Override
    public void execute() {
        if(receiver.getParameters().isAuthorized()) {
            if (pathname.isEmpty()) {
                reply = new Reply(Reply.Code.CODE_211);
            } else {
                File f = new File(pathname);

                if (f.exists()) {
                    reply = f.isDirectory() ? new Reply(Reply.Code.CODE_212) : new Reply(Reply.Code.CODE_213);
                } else {
                    reply = new Reply(Reply.Code.CODE_450);
                }
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_211 == this.reply.getReplyCode()) {
            message = getCode211FormattedString();
        } else if(Reply.Code.CODE_212 == this.reply.getReplyCode()) {
            message = getCode212FormattedString();
        } else if(Reply.Code.CODE_213 == this.reply.getReplyCode()) {
            message = getCode213FormattedString();
        } else if(Reply.Code.CODE_450 == this.reply.getReplyCode()) {
            message = getCode450FormattedString();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private boolean isAbsolutePath(String pathname) {
        return !pathname.isEmpty() && '/' == pathname.charAt(0);
    }

    private String getCode211FormattedString() {
        String mode = receiver.getParameters().getMode().toString().toLowerCase();
        String type = receiver.getParameters().getType().toString().toLowerCase();
        String form = receiver.getParameters().getForm().toString().toLowerCase().replace("_", "-");
        String structure = receiver.getParameters().getStructure().toString().toLowerCase();
        String message = String.format(RESPONSE_211_FORMAT, mode, type, form, structure);
        return String.format(this.reply.getMessage(), message);
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
