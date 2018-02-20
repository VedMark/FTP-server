package server;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import javax.net.ServerSocketFactory;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Properties;

public class FTPServer {
    private ServerSocket socket;
    private TextArea view;
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

    private ServerSocket getPiSocket(Integer port, Integer maxUsers) throws IOException{
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocket.setReceiveBufferSize(maxUsers);
        return serverSocket;
    }

    private void printAddresses() throws SocketException{
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                System.out.println(i.getHostAddress());
            }
        }
    }
    public void run() {
        try {
//            printAddresses();
//            System.out.println("address:" + this.socket.getInetAddress().toString() +":"+ this.socket.getLocalPort());
//            System.out.println("endpoint:" + this.socket.getLocalSocketAddress().toString());

            while(true) {
                Socket socket = this.socket.accept();
                System.out.println("connected user: " +
                        socket.getInetAddress().toString()+ ":" +
                        socket.getPort());

                FTPServerPI pi = new FTPServerPI(socket, this.ftpProperties);
                pi.start();
            }
        }
        catch (IOException exception) {
            System.out.println("I/O exception");
        }
        finally {
            stop();
        }
    }

    public void stop() {
        try {
            if(this.socket != null) {
                this.socket.close();
            }
        }
        catch (IOException exception) {
            this.socket = null;
        }
    }

    public void setView(TextArea view) {
        this.view = view;
    }

    public void log(String info) {
        final String text = info + "\n";
        System.out.println(text);
        Platform.runLater(() -> this.view.appendText(text));
    }
}
