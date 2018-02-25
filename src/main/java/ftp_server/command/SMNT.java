package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class SMNT implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String pathname;

    public SMNT(FTPServerDTP serverDTP, String pathname) {
        this.receiver = serverDTP;
        this.pathname = pathname;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}

