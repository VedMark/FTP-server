package ftp_server.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ActiveConnection extends Connection {
    ActiveConnection(FTPServerDTP serverDTP, FTPTransferParameters parameters) {
        super(serverDTP, parameters);
    }

    @Override
    public void negotiate() throws IOException {
        channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(parameters.getUserAddress());
    }

    protected void destroy() { }
}
