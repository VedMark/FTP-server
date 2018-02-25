package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class STOU implements Command {
    private FTPServerDTP receiver;

    public STOU(FTPServerDTP serverDTP) {
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
