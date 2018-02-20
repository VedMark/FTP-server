import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.FTPServer;

import java.io.IOException;

public class Main extends Application {
    private static final String CONFIG_FILE = "config.properties";

    private TextArea logArea;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        this.logArea = new TextArea();
        this.logArea.setEditable(false);

        root.getChildren().add(this.logArea);

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();

        new Thread(() -> {
            try {
                FTPServer server = new FTPServer(CONFIG_FILE);
                server.setView(this.logArea);
                server.run();
            }
            catch (IOException exception) {
                System.out.println("I/O exception");
                System.exit(1);
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
