package ftp_server.server;

public interface DataConnectionListener {
    enum Result {
        GOOD, BAD
    }

    void onActionNegotiated(Result result) throws ServiceChannelException;
    void onTransferFinished(Result result) throws ServiceChannelException;
    void onConnectionAborted() throws ServiceChannelException;
}
