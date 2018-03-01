package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.ServerProperties;
import ftp_server.server.DataTransferProcess;

import java.util.MissingResourceException;

public class USER implements Command {

    private DataTransferProcess receiver;
    private Reply reply = null;
    private String username;

    public USER(DataTransferProcess serverDTP, String username) {
        this.receiver = serverDTP;
        this.username = username;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {
            reply = new Reply(Reply.Code.CODE_530);
        } else if(!isaValidString()) {
            reply = new Reply(Reply.Code.CODE_501);
        } else {
            this.receiver.getParameters().setUsername(this.username);

            try {
                checkIfNoPasswordForUser();
            } catch (MissingResourceException ignored) {
                reply = new Reply(Reply.Code.CODE_331);
            }
        }
    }

    private void checkIfNoPasswordForUser() {
        if (ServerProperties.getPassword(this.username).isEmpty()) {
            reply = new Reply(Reply.Code.CODE_230);
            this.receiver.getParameters().setAuthorized(true);
            this.receiver.getParameters().setHome(ServerProperties.getHome(this.receiver.getParameters().getUsername()));
        } else {
            reply = new Reply(Reply.Code.CODE_331);
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
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode331FormattedString() {
        return String.format(this.reply.getMessage(), this.receiver.getParameters().getUsername());
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), "You are already logged in");
    }

    private boolean isaValidString() {
        return !(this.username.isEmpty() || this.username.contains("\r") || this.username.contains("\n"));
    }
}
