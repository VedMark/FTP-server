package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class PWD implements Command {
    private FTPServerDTP receiver;

    public PWD(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
