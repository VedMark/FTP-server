package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class NLST implements Command {
    private FTPServerDTP receiver;
    private String pathname;

    public NLST(FTPServerDTP serverDTP, String pathname) {
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
