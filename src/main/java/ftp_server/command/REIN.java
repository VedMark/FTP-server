package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.ConfigException;
import ftp_server.server.ServerProperties;
import ftp_server.server.DataTransferProcess;

public class REIN implements Command {
    private DataTransferProcess receiver;
    Reply reply;

    public REIN(DataTransferProcess serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public void execute() {
        receiver.getParameters().reset();
        reply = new Reply(Reply.Code.CODE_220);
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_220 == this.reply.getReplyCode()) {
            message = getCode220FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode220FormattedString() {
        Integer res;
        try {
            res = ServerProperties.getTimeout() / (60 * 1000); // converting milliseconds to minutes
        } catch (ConfigException e) {
            res = 0;
        }

        return String.format(reply.getMessage(), res, res == 1 ? "" : "s");
    }
}
