package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPProperties;
import ftp_server.server.FTPServerDTP;

public class PASS implements Command {
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
            message = this.reply.getMessage();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private Boolean verifyAccount() {
        return FTPProperties.getPassword(this.receiver.getParameters().getUsername()).equals(this.password);
    }
}
