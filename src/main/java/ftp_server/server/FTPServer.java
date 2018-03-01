package ftp_server.server;

import ftp_server.Main;
import ftp_server.view.View;
import org.apache.log4j.Logger;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private ServerSocket socket;
    private View view;

    public FTPServer() throws IOException, ConfigException {
        this.socket = getPiSocket(FTPProperties.getPortPI(), FTPProperties.getCapacity());
    }

    private ServerSocket getPiSocket(Short port, Integer maxUsers) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        serverSocket.bind(new InetSocketAddress("", port));
        serverSocket.setReceiveBufferSize(maxUsers);
        return serverSocket;
    }

    public void run() throws IOException, ConfigException {
        while(true) {
            Socket socket = this.socket.accept();
            socket.setSoTimeout(FTPProperties.getTimeout());

            FTPServerPI pi = null;
            try {
                pi = new FTPServerPI(socket, this.view);
                pi.start();
            } catch (ServiceChannelException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void destroy() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        }
        catch (IOException exception) {
            this.socket = null;
        }
    }

    public void setView(View view) {
        this.view = view;
    }
}
