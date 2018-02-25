package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class CDUP implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public CDUP(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}
