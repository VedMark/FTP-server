package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class MODE implements Command {
    private FTPServerDTP receiver;
    private String mode_code;

    public MODE(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

    @Override
    public void setParam(String param) {
        this.mode_code = param;
    }
}
