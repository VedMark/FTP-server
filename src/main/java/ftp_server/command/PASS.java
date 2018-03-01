package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPProperties;
import ftp_server.server.FTPServerDTP;

import java.util.MissingResourceException;

public class PASS implements Command {
    private static final String AUTHENTICATION_FAILED_MESSAGE = "Login authentication failed";
    private FTPServerDTP receiver;
    Reply reply;
    private String password;

    public PASS(FTPServerDTP serverDTP, String password) {
        this.receiver = serverDTP;
        this.password = password;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().getUsername().isEmpty()) {
            reply = new Reply(Reply.Code.CODE_503);
        } else if(this.verifyAccount()) {
            this.receiver.getParameters().setPassword(this.password);
            this.receiver.getParameters().setHome(FTPProperties.getHome(this.receiver.getParameters().getUsername()));
            this.receiver.getParameters().setAuthorized(true);
            reply = new Reply(Reply.Code.CODE_230);
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;

        if(Reply.Code.CODE_230 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_503 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = this.getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(),AUTHENTICATION_FAILED_MESSAGE);
    }

    private Boolean verifyAccount() {
        try {
            return FTPProperties.getPassword(this.receiver.getParameters().getUsername()).equals(this.password);
        }
        catch(MissingResourceException exception) {
            return false;
        }
    }
}
