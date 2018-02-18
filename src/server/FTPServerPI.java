package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

class FTPServerPI implements Runnable {
    final private String CHAR_ENCODING = "UTF-8";

    private FTPServerDTP serverDTP;

    private SocketChannel channel = null;
    private Scanner scanner = null;
    private Thread thread = null;

    FTPServerPI(SocketChannel socket) throws IOException {
        this.serverDTP = new FTPServerDTP();

        this.channel = socket;
        this.channel.configureBlocking(true);

        this.scanner = new Scanner(this.channel, CHAR_ENCODING);

        sendMessage("----------220-Welcome to FTP-server----------");
    }

    public void start() {
        if(thread == null) {
            this.thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {
        System.out.println("Server PI: run()");
    }

    public void destroy() {

    }

    private void sendMessage(String message) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes(CHAR_ENCODING));

        while (buffer.hasRemaining()) {
            if(channel != null) {
                channel.write(buffer);
            }
        }
    }

    public void receiveMessage() {

    }
}
