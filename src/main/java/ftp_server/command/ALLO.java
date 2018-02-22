package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class ALLO implements Command {
    private FTPServerDTP receiver;
    private String dec_int;

    public ALLO(FTPServerDTP serverDTP, String dec_int) {
        this.receiver = serverDTP;
        this.dec_int = dec_int;
    }

    @Override
    public Reply execute() {
        return null;
    }

}
