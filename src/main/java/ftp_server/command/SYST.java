package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class SYST implements Command {
    private FTPServerDTP receiver;

    public SYST(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
