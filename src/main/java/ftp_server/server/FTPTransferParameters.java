package ftp_server.server;

public class FTPTransferParameters {
    private static final String     DEFAULT_WORKING_DIR = "/";
    private static final Type       DEFAULT_TYPE        = Type.ASCII;
    private static final Mode       DEFAULT_MODE        = Mode.Stream;
    private static final Structure  DEFAULT_STRUCTURE   = Structure.FILE;

    private String      username    = "";
    private String      password    = "";
    private String      home        = "";
    private String      workingDir  = DEFAULT_WORKING_DIR;
    private Type        type        = DEFAULT_TYPE;
    private Mode        mode        = DEFAULT_MODE;
    private Structure   structure   = DEFAULT_STRUCTURE;
    private boolean     authorized;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHome() {
        return this.home;
    }

    public void setHome(String home) {
        this.home = home.endsWith(DEFAULT_WORKING_DIR) ? home.substring(home.length() - 1) : home;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Boolean isAuthorized() {
        return this.authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getWorkingDir() {
        return this.workingDir;
    }

    public void setWorkingDir(String dir) {
        this.workingDir = dir;
    }

    public void reset() {
        username = "";
        password = "";
        home = "";
        workingDir = DEFAULT_WORKING_DIR;
        type = DEFAULT_TYPE;
        mode = DEFAULT_MODE;
        structure = DEFAULT_STRUCTURE;
        authorized = false;
    }
}
