package server;

import logging.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Properties;

public class FTPServer implements Observable {
    private RequestController requestController;
    private ServerSocketChannel socketPI;
    private ArrayList<Observer> views = new ArrayList<>();

    public FTPServer(String configFile) {
        try {
            FTPProperties properties = getFtpProperties(configFile);
            this.socketPI = getPiSocket(properties.getPort(), properties.getCapacity());
        }
        catch (FileNotFoundException exception) {
            System.out.println("Configuration not found error");
            System.exit(1);
        }
        catch (IOException exception) {
            System.out.println("I/O exception");
            System.exit(1);
        }
    }

    private FTPProperties getFtpProperties(String configFile) throws IOException {
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        return new FTPProperties(props);
    }

    private ServerSocketChannel getPiSocket(Integer port, Integer maxUsers) throws IOException{
        ServerSocketChannel socket = ServerSocketChannel.open();
        socket.configureBlocking(true);
        socket.socket().setReceiveBufferSize(maxUsers);
        socket.socket().bind(new InetSocketAddress(Integer.getInteger("ftp.port", port)));
        return socket;
    }

    public void run() {
        try {
            while(true) {
                try {
                    Thread.sleep(5000);
                }
                catch(Exception ex) {

                }
                System.out.println("Yohoo!!!");
                this.log("Yohoo!!!");
                SocketChannel socketChannel = this.socketPI.accept();
                FTPServerPI pi = new FTPServerPI(socketChannel);
                pi.start();
            }
        }
        catch (IOException exception) {
            System.out.println();
        }
        finally {
            stop();
        }
    }

    public void stop() {
        try {
            if(this.socketPI != null) {
                this.socketPI.close();
            }
        }
        catch (IOException exception) {
            this.socketPI = null;
        }
    }

    public void addView(Observer view) {
        this.views.add(view);
    }

    public void removeView(Observer view) {
        this.views.remove(view);
    }

    public void notifyViews(ArrayList<Observer> observers) {
        for(Observer view : this.views) {
            if(view != null) {
                view.notify();
            }
        }
    }

    public void log(String message) {
        for(Observer view : this.views) {
            if(view != null) {
                view.update(message);
            }
        }
    }
}
