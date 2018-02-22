package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class PASS implements Command {
    private FTPServerDTP receiver;
    private String password;

    public PASS(FTPServerDTP serverDTP, String password) {
        this.receiver = serverDTP;
        this.password = password;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
