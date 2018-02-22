package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class MODE implements Command {
    private FTPServerDTP receiver;
    private String mode_code;

    public MODE(FTPServerDTP serverDTP, String mode_code) {
        this.receiver = serverDTP;
        this.mode_code = mode_code;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
