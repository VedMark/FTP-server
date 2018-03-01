package ftp_server.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FTPServerDTP {

    private Connection connection = null;
    private FTPTransferParameters parameters = new FTPTransferParameters();

    private Set<ConnectionListenerDTP> listeners = Collections.synchronizedSet(new HashSet<>());

    private Boolean isConnectionOpen = false;

    public FTPTransferParameters getParameters() {
        return parameters;
    }

    public void addListener(ConnectionListenerDTP listener) {
            listeners.add(listener);
    }

    public void removeListener(ConnectionListenerDTP listener) {
        listeners.remove(listener);
    }

    public void start() throws ServiceChannelException {
        try {
            connection = parameters.isPassiveProcess() ?
                    new PassiveConnection(this, parameters) :
                    new ActiveConnection(this, parameters);

            connection.negotiate();
            for (ConnectionListenerDTP listener : listeners) {
                listener.onActionNegotiated(ConnectionListenerDTP.Result.GOOD);
            }
            isConnectionOpen = true;
            connection.start();

        } catch (IOException exception) {
            isConnectionOpen = false;
            for (ConnectionListenerDTP listener : listeners) {
                listener.onActionNegotiated(ConnectionListenerDTP.Result.BAD);
            }
        }
    }

    public void retrieveFile(Path path) {
        if(connection != null) {
            connection.sendFile(path);
        }
    }

    public void receiveFile(Path path) {
        if(connection != null) {
            connection.receiveFile(path);
        }
    }

    public Boolean isConnectionOpen() {
        return isConnectionOpen;
    }

    public void abortConnection() throws ServiceChannelException {
        connection.stop();
        isConnectionOpen = false;

        for(ConnectionListenerDTP listener: listeners) {
            listener.onConnectionAborted();
        }
    }

    public void handleCompleted(boolean completed) {
        ConnectionListenerDTP.Result result = completed ?
                ConnectionListenerDTP.Result.GOOD :
                ConnectionListenerDTP.Result.BAD;

        for(ConnectionListenerDTP listener: listeners) {
            try {
                listener.onTransferFinished(result);
            } catch (ServiceChannelException ignored) { }
        }
    }
}
