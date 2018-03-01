package ftp_server.command;

import ftp_server.reply.Reply;

public class SYST implements Command {
    Reply reply;

    public SYST() {
    }

    @Override
    public void execute() {
        reply = new Reply(Reply.Code.CODE_215);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;

        if(Reply.Code.CODE_215 == this.reply.getReplyCode()) {
            message = getCode215FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode215FormattedString() {
        String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
        return String.format(this.reply.getMessage(), os);
    }
}
