package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class STRU implements Command {
    private FTPServerDTP receiver;
    private String structure_code;

    public STRU(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

    @Override
    public void setParam(String param) {
        this.structure_code = param;
    }
}
