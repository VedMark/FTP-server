package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class HELP implements Command {
    private FTPServerDTP receiver;
    private String string;

    public HELP(FTPServerDTP serverDTP, String string) {
        this.receiver = serverDTP;
        this.string = string;
    }

    @Override
    public Reply execute() {
        return null;
    }

}