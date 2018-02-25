package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPProperties;
import ftp_server.server.FTPServerDTP;

import java.util.MissingResourceException;

public class USER implements Command {

    private FTPServerDTP receiver;
    private Reply reply = null;
    private String username;

    public USER(FTPServerDTP serverDTP, String username) {
        this.receiver = serverDTP;
        this.username = username;
    }

    @Override
    public void execute() {
        if(!isaValidString()) {
            reply = new Reply(Reply.Code.CODE_501);
        } else {
            this.receiver.getParameters().setUsername(this.username);

            try {
                reply = FTPProperties.getPassword(this.username).isEmpty() ?
                        new Reply(Reply.Code.CODE_230) :
                        new Reply(Reply.Code.CODE_331);
            } catch (MissingResourceException ignored) {
                reply = new Reply(Reply.Code.CODE_331);
            }
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_230 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_331 == this.reply.getReplyCode()) {
            message = getCode331FormattedString();
        } else if(Reply.Code.CODE_501 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode331FormattedString() {
        return String.format(this.reply.getMessage(), this.receiver.getParameters().getUsername());
    }

    private boolean isaValidString() {
        return !(this.username.isEmpty() || this.username.contains("\r") || this.username.contains("\n"));
    }
}
