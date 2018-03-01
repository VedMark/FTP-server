package ftp_server.server;

import java.net.InetSocketAddress;

public class TransferParameters {

    private static final String     DEFAULT_WORKING_DIR = "/";
    private static final TypeEnum DEFAULT_TYPE        = TypeEnum.ASCII;
    private static final ModeEnum DEFAULT_MODE        = ModeEnum.Stream;
    private static final FormEnum DEFAULT_FORM        = FormEnum.NON_PRINT;
    private static final StructureEnum DEFAULT_STRUCTURE   = StructureEnum.FILE;

    private String              username        = "";
    private String              password        = "";
    private String              home            = "";
    private String              workingDir      = DEFAULT_WORKING_DIR;
    private InetSocketAddress   userAddress     = null;
    private InetSocketAddress   serverAddress   = null;
    private Boolean             isPassive       = false;
    private TypeEnum type            = DEFAULT_TYPE;
    private ModeEnum mode            = DEFAULT_MODE;
    private FormEnum form            = DEFAULT_FORM;
    private StructureEnum structure       = DEFAULT_STRUCTURE;
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

    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public ModeEnum getMode() {
        return this.mode;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }

    public FormEnum getForm() {
        return form;
    }

    public void setForm(FormEnum form) {
        this.form = form;
    }

    public StructureEnum getStructure() {
        return this.structure;
    }

    public void setStructure(StructureEnum structure) {
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
