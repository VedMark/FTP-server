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

        sendMessage(getFormattedMessage(new Reply(Reply.Code.CODE_220)));
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
            Reply reply = request.execute();
            sendMessage(this.getFormattedMessage(reply));

            if(request instanceof QUIT) {
                break;
            }
        }
    }

    private String getFormattedMessage(Reply reply) throws SocketException{
        String result;

        switch (reply.getReplyCode()) {
            case CODE_215: result = getCode215FormattedString(reply); break;
            case CODE_220: result = getCode220FormattedString(reply); break;
            case CODE_331: result = getCode331FormattedString(reply); break;

            default: result  = reply.getMessage();
        }

        return result;
    }

    private String getCode215FormattedString(Reply reply) {
        String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
        return String.format(reply.getMessage(), os);
    }

    private String getCode220FormattedString(Reply reply) throws SocketException {
        Integer res = this.socket.getSoTimeout() / (60 * 1000); // converting milliseconds to minutes
        return String.format(reply.getMessage(), res, res == 1 ? "" : "s");
    }

    private String getCode331FormattedString(Reply reply) {
        return String.format(reply.getMessage(), this.serverDTP.getParameters().getUsername());
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.flush();
        this.updateDialog(message);
    }

    private Command receiveMessage() throws IOException {
        String message = reader.readLine();
        this.updateDialog(message + "\n");
        return cmdController.createCommand(message);
    }

    private void updateDialog(String info) {
        this.view.update(info);
    }
}
