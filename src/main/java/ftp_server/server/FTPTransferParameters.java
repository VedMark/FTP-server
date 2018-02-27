package ftp_server.server;

import java.net.InetSocketAddress;

public class FTPTransferParameters {

    private static final String     DEFAULT_WORKING_DIR = "/";
    private static final Type       DEFAULT_TYPE        = Type.ASCII;
    private static final Mode       DEFAULT_MODE        = Mode.Stream;
    private static final Form       DEFAULT_FORM        = Form.NON_PRINT;
    private static final Structure  DEFAULT_STRUCTURE   = Structure.FILE;

    private String              username        = "";
    private String              password        = "";
    private String              home            = "";
    private String              workingDir      = DEFAULT_WORKING_DIR;
    private InetSocketAddress   userAddress     = null;
    private InetSocketAddress   serverAddress   = null;
    private Boolean             isPassive       = true;
    private Type                type            = DEFAULT_TYPE;
    private Mode                mode            = DEFAULT_MODE;
    private Form                form            = DEFAULT_FORM;
    private Structure           structure       = DEFAULT_STRUCTURE;
    private Boolean             authorized      = false;

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

    public String getWorkingDir() {
        return this.workingDir;
    }

    public void setWorkingDir(String dir) {
        this.workingDir = dir;
    }

    public InetSocketAddress getUserAddress() {
        return this.userAddress;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(InetSocketAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void toActiveProcess(InetSocketAddress address) {
        this.userAddress = address;
        isPassive = false;
    }

    public void toPassiveProcess() {
        this.userAddress = null;
        isPassive = true;
    }

    public Boolean isPassiveProcess() {
        return isPassive;
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

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
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

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public void reset() {
        this.username = "";
        this.password = "";
        this.home = "";
        this.workingDir = DEFAULT_WORKING_DIR;
        this.userAddress = null;
        this.isPassive = true;
        this.type = DEFAULT_TYPE;
        this.mode = DEFAULT_MODE;
        this.form = DEFAULT_FORM;
        this.structure = DEFAULT_STRUCTURE;
        this.authorized = false;
    }
}
