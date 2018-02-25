package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

public class SYST implements Command {

    public SYST() {
    }

    @Override
    public Reply execute() {
        return new Reply(Reply.Code.CODE_215);
    }

}
