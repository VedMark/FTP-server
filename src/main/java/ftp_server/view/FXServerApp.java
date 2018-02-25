package ftp_server.view;

import ftp_server.Main;
import ftp_server.server.ConfigException;
import ftp_server.server.FTPServer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class FXServerApp extends Application {
    private static final Logger log = LogManager.getLogger(Main.class);

    private LogComponent logComponent;
    private FTPServer server;
    private Thread serverThread;

    public static void start() {
        Application.launch();
    }

    @Override
    public void stop() {
        this.server.destroy();
        this.serverThread.interrupt();
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        this.logComponent = new LogComponent();

        root.getChildren().add(this.logComponent.getLogArea());

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        //primaryStage.show();

        this.serverThread = new Thread("Server Thread") {
            @Override
            public void run() {
                try {
                    server = new FTPServer();
                    server.setView(logComponent);
                    server.run();
                } catch (IOException | ConfigException exception) {
                    if (server != null) {
                        server.destroy();
                    }
                    log.fatal(exception.getMessage());
                    System.exit(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.serverThread.setDaemon(true);
        this.serverThread.start();
    }
}
