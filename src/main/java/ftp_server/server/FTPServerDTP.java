package ftp_server.server;

public class FTPServerDTP {
    private FTPTransferParameters parameters;

    public FTPServerDTP() {
        parameters = new FTPTransferParameters();
    }

    public void sendData() {
//        ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes(CHAR_ENCODING));
//        while (buffer.hasRemaining()) {
//        }
    }

    public FTPTransferParameters getParameters() {
        return parameters;
    }
}
