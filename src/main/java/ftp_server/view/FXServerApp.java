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

    public static void start() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        this.logComponent = new LogComponent();

        root.getChildren().add(this.logComponent.getLogArea());

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        //primaryStage.show();

        new Thread(() -> {
            try {
                this.server = new FTPServer();
                this.server.setView(this.logComponent);
                this.server.run();
            }
            catch (IOException | ConfigException exception) {
                this.server.destroy();
                log.fatal(exception.getMessage());
                System.exit(1);
            }
        }).start();
    }
}
