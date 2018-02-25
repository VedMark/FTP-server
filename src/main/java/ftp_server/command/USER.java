package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.ConfigException;
import ftp_server.server.FTPProperties;
import ftp_server.server.FTPServerDTP;
import ftp_server.server.FTPTransferParameters;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class USER implements Command {

    private FTPServerDTP receiver;
    private String username;

    public USER(FTPServerDTP serverDTP, String username) {
        this.receiver = serverDTP;
        this.username = username;
    }

    @Override
    public Reply execute() {
        if(!isaValidString()) {
            return new Reply(Reply.Code.CODE_501);
        }

        this.receiver.getParameters().setUsername(this.username);

        try {
            return FTPProperties.getPassword(this.username).isEmpty() ?
                    new Reply(Reply.Code.CODE_230) :
                    new Reply(Reply.Code.CODE_331);
        }
        catch (MissingResourceException ignored) {
            return new Reply(Reply.Code.CODE_331);
        }
    }

    private boolean isaValidString() {
        return !(this.username.isEmpty() || this.username.contains("\r") || this.username.contains("\n"));
    }
}
