package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class FTPServerPI implements Runnable {
    final private String CHAR_ENCODING = "UTF-8";

    private RequestController requestController = new RequestController();
    private FTPServerDTP serverDTP = new FTPServerDTP();

    private Socket socket;
    private FTPProperties ftpProperties;
    private Thread thread;
    private final BufferedReader reader;
    private final DataOutputStream writer;

    FTPServerPI(Socket socket, FTPProperties ftpProperties) throws IOException {
        this.socket = socket;
        this.ftpProperties = ftpProperties;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new DataOutputStream(socket.getOutputStream());

        sendMessage("----------220-Welcome to FTP-server----------");
    }

    public void start() {
        if(this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void run() {
        System.out.println("Server PI: run()");
    }

    public void destroy() {

    }

    private void sendMessage(String message) throws IOException{
        //ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes(CHAR_ENCODING));

        //while (buffer.hasRemaining()) {
            if(this.socket != null) {
                this.writer.write((message + "\n").getBytes(CHAR_ENCODING));
                this.writer.flush();
            }
        //}
    }

    public void receiveMessage() {

    }
}
