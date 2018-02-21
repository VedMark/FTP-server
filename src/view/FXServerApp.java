package view;

import server.FTPServer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FXServerApp extends Application {
    private static final String CONFIG_FILE = "config.properties";

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
                this.server = new FTPServer(CONFIG_FILE);
                this.server.setView(this.logComponent);
                this.server.run();
            }
            catch (IOException exception) {
                System.out.println(exception.getMessage());
                this.server.destroy();
                System.exit(1);
            }
        }).start();
    }
}
