package ftp_server.server;

import ftp_server.Main;
import ftp_server.command.Command;
import ftp_server.command.QUIT;
import ftp_server.reply.Reply;
import ftp_server.view.View;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

class ProtocolInterpreter implements Runnable, DataConnectionListener {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private DataTransferProcess serverDTP = new DataTransferProcess();
    private CommandController cmdController = new CommandController(this.serverDTP);

    private Thread thread = null;

    private View view;

    ProtocolInterpreter(Socket socket, View view) throws ServiceChannelException {
        this.socket = socket;
        this.view = view;

        InetSocketAddress addr = new InetSocketAddress(socket.getLocalAddress().getHostAddress(), socket.getPort());
        this.serverDTP.getParameters().setServerAddress(addr);
        this.serverDTP.addListener(this);

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendMessage(getCode220FormattedString(new Reply(Reply.Code.CODE_220)));
        } catch (IOException exception) {
            throw new ServiceChannelException(exception.getMessage());
        }
    }

    public void start() {
        if(this.thread == null) {
            this.thread = new Thread(
                    this,
                    "Thread for " + this.socket.getInetAddress() + " " + this.socket.getPort()
            );
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
        catch (IOException | ServiceChannelException exception) {
            this.stop();
            log.error(exception.getMessage());
        }
        this.stop();
    }

    private void listenToConnections() throws IOException, ServiceChannelException {
        while (true) {
            Command request = receiveMessage();
            request.execute();
            String message = request.getResponseMessage();
            if(message != null) {
                sendMessage(message);
            }

            if (request instanceof QUIT) {
                break;
            }
        }
    }

    private enum Sender {
        FROM_SERVER, FROM_CLIENT
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.flush();
        this.updateDialog(message, Sender.FROM_SERVER);
    }

    private Command receiveMessage() throws IOException {
        String message = reader.readLine();
        if(message != null) {
            this.updateDialog(message + "\n", Sender.FROM_CLIENT);
            return cmdController.createCommand(message);
        } else {
            throw new SocketException("Connection unexpectedly closed by remote passiveSocket");
        }
    }

    private void updateDialog(String info, Sender sender) {
        String preffix;
        if(sender == Sender.FROM_SERVER) {
            preffix = "---->";
        } else {
            preffix = "<----";
        }
        this.view.update(preffix + " " + socket.getRemoteSocketAddress().toString() + " " + info);
    }

    @Override
    public void onActionNegotiated(Result result) throws ServiceChannelException {
        String message;
        if(result == Result.GOOD) {
            message = serverDTP.getParameters().isPassiveProcess() ?
                    getCode227FormattedString(new Reply(Reply.Code.CODE_227)) :
                    getCode200FormattedString(new Reply(Reply.Code.CODE_200));
        } else {
            message = new Reply(Reply.Code.CODE_425).getMessage();
        }

        try {
            sendMessage(message);
        } catch (IOException exception) {
            throw new ServiceChannelException(exception.getMessage());
        }
    }

    @Override
    public void onTransferFinished(Result result) throws ServiceChannelException {
        String message = result == Result.GOOD ?
                String.format(new Reply(Reply.Code.CODE_226).getMessage(), "File transfer successful") :
                new Reply(Reply.Code.CODE_451).getMessage();
        try {
            sendMessage(message);
        }
        catch (IOException exception) {
            throw new ServiceChannelException(exception.getMessage());
        }
    }

    @Override
    public void onConnectionAborted() throws ServiceChannelException {
        try {
            sendMessage(String.format(
                    new Reply(Reply.Code.CODE_226).getMessage(),
                    "Abort command was successfully processed"
            ));
        }
        catch (IOException exception) {
            throw new ServiceChannelException(exception.getMessage());
        }
    }

    private String getCode200FormattedString(Reply reply) {
        return String.format(reply.getMessage(), "PORT command successful");
    }

    private String getCode220FormattedString(Reply reply) {
        Integer res; // converting milliseconds to minutes
        try {
            res = ServerProperties.getTimeout() / (60 * 1000);
        } catch (ConfigException e) {
            res = 0;
        }
        return String.format(reply.getMessage(), res, res == 1 ? "" : "s");
    }

    private String getCode227FormattedString(Reply reply) {
        InetSocketAddress serverAddress = serverDTP.getParameters().getServerAddress();
        String[] arr = serverAddress.getAddress().getHostAddress().split("\\.");
        String el4 = String.valueOf(serverAddress.getPort() / 256);
        String el5 = String.valueOf(serverAddress.getPort() % 256);

        return String.format(reply.getMessage(), arr[0], arr[1], arr[2], arr[3], el4, el5);
    }
}
