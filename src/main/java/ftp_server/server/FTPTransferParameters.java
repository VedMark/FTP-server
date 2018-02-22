package ftp_server.server;

public class FTPTransferParameters {
    final static private Type DEFAULT_TYPE = Type.ASCII;
    final static private Mode DEFAULT_MODE = Mode.Stream;
    final static private Structure DEFAULT_STRUCTURE = Structure.FILE;

    private String username = null;
    private String password = null;
    private Type type = DEFAULT_TYPE;
    private Mode mode = DEFAULT_MODE;
    private Structure structure = DEFAULT_STRUCTURE;
}
