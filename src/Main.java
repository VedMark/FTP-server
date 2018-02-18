import server.FTPServer;
import logging.LogWindow;

public class Main {
    private static final String configFile = "config.properties";

    public static void main(String[] args) {
        FTPServer ftpServer = new FTPServer(configFile);

        LogWindow logWindow = new LogWindow();
        ftpServer.addView(logWindow);
        logWindow.initialize();

        ftpServer.run();
    }
}
