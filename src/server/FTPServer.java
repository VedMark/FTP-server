package server;

import view.View;

import javax.net.ServerSocketFactory;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Properties;

public class FTPServer {
    private ServerSocket socket;
    private View view;
    private final FTPProperties ftpProperties;

    public FTPServer(String configFile) throws IOException {
        this.ftpProperties = getFtpProperties(configFile);
        this.socket = getPiSocket(this.ftpProperties.getPortPI(), this.ftpProperties.getCapacity());
    }

    private FTPProperties getFtpProperties(String configFile) throws IOException {
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        return new FTPProperties(props);
    }

    private ServerSocket getPiSocket(Integer port, Integer maxUsers) throws IOException {
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocket.setReceiveBufferSize(maxUsers);
        return serverSocket;
    }

    public void run() throws IOException{
        while(true) {
            Socket socket = this.socket.accept();

            // TODO: remove
            System.out.println("connected user: " +
                    socket.getInetAddress().toString() + ":" +
                    socket.getPort());

            FTPServerPI pi = new FTPServerPI(socket, this.ftpProperties, this.view);
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
