package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

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
            String home = this.receiver.getParameters().getHome();
            String workingDir = this.receiver.getParameters().getWorkingDir();

            String prevDir = getCanonicalPath(new File(home + workingDir, ".."));

            if(prevDir.length() < receiver.getParameters().getHome().length()) {
                prevDir =  receiver.getParameters().getHome();
            }

            String newDir = prevDir.substring(receiver.getParameters().getHome().length());
            receiver.getParameters().setWorkingDir(newDir.isEmpty() ? "/" : newDir);
            reply = new Reply(Reply.Code.CODE_200);

        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private String getCanonicalPath(File f) {
        String prevDir;
        try {
            prevDir = f.getCanonicalPath();
        } catch (IOException e) {
            prevDir = f.getAbsolutePath();
        }
        return prevDir;
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
