package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class RNTO implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String pathname;

    public RNTO(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}
