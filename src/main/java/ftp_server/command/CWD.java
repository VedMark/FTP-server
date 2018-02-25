package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

import java.io.File;
import java.io.IOException;

public class CWD implements Command {
    private static final String NO_SUCH_DIR_MESSAGE = "no such directory";

    private FTPServerDTP receiver;
    Reply reply;
    private String pathname;

    public CWD(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            String dir = processPath(pathname);

            if(dir != null && dir.startsWith(receiver.getParameters().getHome())) {
                    String newDir = dir.substring(receiver.getParameters().getHome().length()) + "/";
                    receiver.getParameters().setWorkingDir(newDir);
                    reply = new Reply(Reply.Code.CODE_250);
            } else {
                reply = new Reply(Reply.Code.CODE_550);
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private String processPath(String pathname) {
        File tmpFile = getFile(pathname);

        try {
            tmpFile = new File(tmpFile.getCanonicalPath());
        } catch (IOException e) {
            tmpFile = new File(tmpFile.getAbsolutePath());
        }

        if(!isValidDirectoryName(tmpFile)) {
            return null;
        }
        if(tmpFile.getAbsolutePath().length() < receiver.getParameters().getHome().length()) {
            return receiver.getParameters().getHome();
        }

        return tmpFile.getAbsolutePath();
    }

    private File getFile(String pathname) {
        String home = this.receiver.getParameters().getHome();
        String workingDir = this.receiver.getParameters().getWorkingDir();

        return isAbsolutePath(pathname) ?
                new File(home, pathname.substring(1)) :
                new File(home + workingDir, pathname);
    }

    private boolean isAbsolutePath(String pathname) {
        return !pathname.isEmpty() && '/' == pathname.charAt(0);
    }

    private boolean isValidDirectoryName(File dir) {
        return dir.exists() && dir.isDirectory();
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
        } else {
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
