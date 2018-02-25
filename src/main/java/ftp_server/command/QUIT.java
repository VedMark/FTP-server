package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

import java.util.Arrays;

public class QUIT implements Command {

    public QUIT(FTPServerDTP receiver) {
    }

    @Override
    public Reply execute() {
        return new Reply(Reply.Code.CODE_221);
    }

}
