package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

import java.util.Arrays;

public class QUIT implements Command {
    private FTPServerDTP receiver;

    public QUIT(FTPServerDTP receiver) {
        this.receiver = receiver;
    }

    @Override
    public Reply execute() {
        return new Reply(221, Arrays.asList("Goodbye.", "Logout."));
    }

}
