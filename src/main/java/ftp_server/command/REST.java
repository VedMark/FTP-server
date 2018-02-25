package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class REST implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String marker;

    public REST(FTPServerDTP serverDTP, String marker) {
        this.receiver = serverDTP;
        this.marker = marker;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}
