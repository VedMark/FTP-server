package ftp_server.server;

import ftp_server.Main;
import ftp_server.command.QUIT;
import ftp_server.command.Command;
import ftp_server.reply.Reply;
import ftp_server.view.View;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

class FTPServerPI implements Runnable {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private static final String WELCOME_MESSAGE = "220----------Welcome to FTP-server----------\n";

    private Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private FTPServerDTP serverDTP = new FTPServerDTP();
    private FTPCommandController cmdController = new FTPCommandController(this.serverDTP);

    private Thread thread = null;

    private View view;

    FTPServerPI(Socket socket, View view) throws IOException {
        this.socket = socket;
        this.view = view;

        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

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
                log.error(exception.getMessage());
            }
            this.socket = null;
        }
    }

    public void run() {
        try {
            listenToConnections();
        }
        catch (IOException exception) {
            log.error(exception.getMessage());
        }
        this.stop();
    }

    private void listenToConnections() throws IOException {
        while (true) {
            Command request = receiveMessage();
            if(request instanceof QUIT) {
                break;
            }
            Reply reply = request.execute();
            sendMessage(reply.toString());
        }
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.flush();
        this.updateDialog(message);
    }

    private Command receiveMessage() throws IOException {
        String message = reader.readLine();
        Command request = cmdController.createCommand(message);
        this.updateDialog(message);
        return request;
    }

    private void updateDialog(String info) {
        this.view.update(info);
    }
}
