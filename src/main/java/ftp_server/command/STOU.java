package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import java.lang.UnsupportedOperationException;

public class STOU implements Command {
    private FTPServerDTP receiver;

    public STOU(FTPServerDTP serverDTP) {
        this.receiver = serverDTP;
    }

    @Override
    public Reply execute() {
        return null;
    }

    @Override
    public void setParam(String param) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
