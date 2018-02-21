package server;

import command.QUIT;
import command.Command;
import view.View;

import java.io.*;
import java.net.Socket;

class FTPServerPI implements Runnable {
    final static private String WELCOME_MESSAGE = "----------220-Welcome to FTP-server----------\n";

    private Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private FTPServerDTP serverDTP = new FTPServerDTP();
    private FTPCommandController requestController = new FTPCommandController();
    private FTPProperties ftpProperties;

    private Thread thread = null;

    private View view;

    FTPServerPI(Socket socket, FTPProperties ftpProperties, View view) throws IOException {
        this.socket = socket;
        this.ftpProperties = ftpProperties;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        this.view = view;

        sendMessage(WELCOME_MESSAGE);
    }

    public void start() {
        if(this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void stop() {
        if(this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }

        if(this.socket != null) {
            try{
                this.socket.close();
            }
            catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
            this.socket = null;
        }
    }

    public void run() {
        try {
            while (true) {
                Command request = receiveMessage();

                if(request instanceof QUIT) break;

                request.execute();

                //sendMessage("hello");
            }
        }
        catch (IOException exception) {
            // TODO: send error code
        }
        this.stop();
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.flush();
        this.log(message);
    }

    private Command receiveMessage() throws IOException {
        String message = reader.readLine();

        Command request = FTPCommandController.createRequest(message);
        this.log(message);
        return null;
    }

    private void log(String info) {
        this.view.update(info);
    }
}
