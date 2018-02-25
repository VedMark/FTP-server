package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPProperties;
import ftp_server.server.FTPServerDTP;

import java.util.MissingResourceException;

public class PASS implements Command {
    private FTPServerDTP receiver;
    private String password;

    public PASS(FTPServerDTP serverDTP, String password) {
        this.receiver = serverDTP;
        this.password = password;
    }

    @Override
    public Reply execute() {
        if(this.receiver.getParameters().getUsername().isEmpty()) {
            return new Reply(Reply.Code.CODE_503);
        }

        if(this.verifyAccount()) {
            this.receiver.getParameters().setPassword(this.password);
            this.receiver.getParameters().setHome(FTPProperties.getHome(this.receiver.getParameters().getUsername()));
            this.receiver.getParameters().setAuthorized(true);
            return new Reply(Reply.Code.CODE_230);
        }

        return new Reply(Reply.Code.CODE_530);
    }

    private Boolean verifyAccount() {
        return FTPProperties.getPassword(this.receiver.getParameters().getUsername()).equals(this.password);
    }
}
