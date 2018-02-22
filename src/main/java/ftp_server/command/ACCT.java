package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class ACCT implements Command {
    private FTPServerDTP receiver;
    private String accInfo;

    public ACCT(FTPServerDTP serverDTP, String accInfo) {
        this.receiver = serverDTP;
        this.accInfo = accInfo;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
