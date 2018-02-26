package ftp_server.command;

import ftp_server.server.FTPServerDTP;

public class PORT implements Command {
    private FTPServerDTP receiver;
    private String host_port;

    public PORT(FTPServerDTP serverDTP, String host_port) {
        this.receiver = serverDTP;
        this.host_port = host_port;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        return null;
    }

}

