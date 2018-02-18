import server.FTPServer;
import view.LogWindow;

public class Main {
    private static final String configFile = "config.properties";

    public static void main(String[] args) {
        FTPServer ftpServer = new FTPServer(configFile);

        LogWindow logWindow = new LogWindow();
        logWindow.initialize();
        ftpServer.addView(logWindow);

        ftpServer.run();
    }
}
