package ftp_server.server;

import ftp_server.Main;
import ftp_server.command.Command;
import ftp_server.command.QUIT;
import ftp_server.reply.Reply;
import ftp_server.view.View;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

class FTPServerPI implements Runnable {
    private static final Logger log = Logger.getLogger(Main.class.getName());

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

        sendMessage(getCode220FormattedString(new Reply(Reply.Code.CODE_220)));
    }

    private String getCode220FormattedString(Reply reply) throws SocketException {
        Integer res; // converting milliseconds to minutes
        try {
            res = FTPProperties.getTimeout() / (60 * 1000);
        } catch (ConfigException e) {
            res = 0;
        }

        return String.format(reply.getMessage(), res, res == 1 ? "" : "s");
    }

    public void start() {
        if(this.thread == null) {
            this.thread = new Thread(this, "Thread for " + this.socket.getInetAddress() + " " + this.socket.getPort());
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
            request.execute();
            sendMessage(request.getResponseMessage());

            if(request instanceof QUIT) {
                break;
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.flush();
        this.updateDialog(message);
    }

    private Command receiveMessage() throws IOException {
        String message = reader.readLine();
        if(message != null) {
            this.updateDialog(message + "\n");
            return cmdController.createCommand(message);
        } else {
            throw new SocketException("Connection unexpectedly closed by remote socket");
        }
    }

    private void updateDialog(String info) {
        this.view.update(info);
    }
}
