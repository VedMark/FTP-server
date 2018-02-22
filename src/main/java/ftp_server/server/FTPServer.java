package ftp_server.server;

import ftp_server.view.View;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;

public class FTPServer {
    private static final String CONFIG_FILE = "config";

    private ServerSocket socket;
    private View view;

    public FTPServer() throws IOException, ConfigException {
        FTPProperties ftpProperties = getFtpProperties(CONFIG_FILE);
        this.socket = getPiSocket(ftpProperties.getPortPI(), ftpProperties.getCapacity());
    }

    private FTPProperties getFtpProperties(String configFile) {
        return new FTPProperties(ResourceBundle.getBundle(configFile));
    }

    private ServerSocket getPiSocket(Integer port, Integer maxUsers) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocket.setReceiveBufferSize(maxUsers);
        return serverSocket;
    }

    public void run() throws IOException {
        while(true) {
            Socket socket = this.socket.accept();

            FTPServerPI pi = new FTPServerPI(socket, this.view);
            pi.start();
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
