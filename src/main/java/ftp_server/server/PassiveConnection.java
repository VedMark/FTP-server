package ftp_server.server;

import ftp_server.Main;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class PassiveConnection extends Connection {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private ServerSocketChannel serverChannel = null;

    PassiveConnection(FTPServerDTP serverDTP, FTPTransferParameters parameters) {
        super(serverDTP, parameters);
    }

    @Override
    public void negotiate() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(true);
        try {
            serverChannel.socket().setSoTimeout(FTPProperties.getTimeout());
        } catch (ConfigException e) {
            serverChannel.socket().setSoTimeout(0);
        }
        try {
            serverChannel.socket().bind(new InetSocketAddress("", FTPProperties.getPortDTP()));
        } catch (ConfigException e) {
            log.error("");
            serverChannel.socket().bind(new InetSocketAddress("", 20));
        }

        super.channel = serverChannel.accept();
    }

    protected void destroy() {
        if(serverChannel != null) {
            try {
                serverChannel.close();
            } catch (IOException e) {
                serverChannel = null;
            }
        }
    }
}
