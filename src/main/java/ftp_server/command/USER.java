package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class USER implements Command {
    private FTPServerDTP receiver;
    private String username;

    public USER(FTPServerDTP serverDTP, String username) {
        this.receiver = serverDTP;
        this.username = username;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
