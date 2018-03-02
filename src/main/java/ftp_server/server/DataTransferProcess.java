package ftp_server.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class DataTransferProcess {

    private Connection connection = null;
    private TransferParameters parameters = new TransferParameters();

    private Set<DataConnectionListener> listeners = Collections.synchronizedSet(new HashSet<>());

    private Boolean isConnectionOpen = false;

    public TransferParameters getParameters() {
        return parameters;
    }

    public void addListener(DataConnectionListener listener) {
            listeners.add(listener);
    }

    public void removeListener(DataConnectionListener listener) {
        listeners.remove(listener);
    }

    public void start() throws ServiceChannelException {
        try {
            connection = parameters.isPassiveProcess() ?
                    new PassiveConnection(this, parameters) :
                    new ActiveConnection(this, parameters);

            connection.negotiate();
            for (DataConnectionListener listener : listeners) {
                listener.onActionNegotiated(DataConnectionListener.Result.GOOD);
            }
            isConnectionOpen = true;
            connection.start();

        } catch (IOException exception) {
            isConnectionOpen = false;
            for (DataConnectionListener listener : listeners) {
                listener.onActionNegotiated(DataConnectionListener.Result.BAD);
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


    public void sendListInfo(Path path) {
        if(connection != null) {
            connection.sendString(path);
        }

    }
    public Boolean isConnectionOpen() {
        return isConnectionOpen;
    }

    public void abortConnection() throws ServiceChannelException {
        connection.stop();
        isConnectionOpen = false;

        for(DataConnectionListener listener: listeners) {
            listener.onConnectionAborted();
        }
    }

    public void handleCompleted(boolean completed) {
        DataConnectionListener.Result result = completed ?
                DataConnectionListener.Result.GOOD :
                DataConnectionListener.Result.BAD;

        for(DataConnectionListener listener: listeners) {
            try {
                listener.onTransferFinished(result);
            } catch (ServiceChannelException ignored) { }
        }
    }
}
