package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class SITE implements Command {
    private FTPServerDTP receiver;
    private String string;

    public SITE(FTPServerDTP serverDTP, String string) {
        this.receiver = serverDTP;
        this.string = string;
    }

    @Override
    public Reply execute() {
        return null;
    }

}