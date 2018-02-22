package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class DELE implements Command {
    private FTPServerDTP receiver;
    private String pathname;

    public DELE(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
