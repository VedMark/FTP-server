package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class REIN implements Command {
    private FTPServerDTP receiver;
    Reply reply;

    public REIN(FTPServerDTP serverDTP) {
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
