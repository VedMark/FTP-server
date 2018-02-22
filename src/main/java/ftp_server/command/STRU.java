package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class STRU implements Command {
    private FTPServerDTP receiver;
    private String structure_code;

    public STRU(FTPServerDTP serverDTP, String structure_code) {
        this.receiver = serverDTP;
        this.structure_code = structure_code;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
