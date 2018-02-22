package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class DELE implements Command {
    private FTPServerDTP receiver;
    private String pathname;

    public DELE(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

    @Override
    public void setParam(String param) {
        this.pathname = param;
    }
}
