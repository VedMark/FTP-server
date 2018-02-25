package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class TYPE implements Command {
    private FTPServerDTP receiver;
    Reply reply;
    private String type_code;

    public TYPE(FTPServerDTP serverDTP, String type_code) {
        this.receiver = serverDTP;
        this.type_code = type_code;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}
