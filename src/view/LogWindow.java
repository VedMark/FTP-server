package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class LogWindow extends Application implements Observer, Runnable {
    private TextArea logArea;
    private Thread thread = new Thread(this);

    public void initialize() {
        this.thread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();

        this.logArea = new TextArea();
        this.logArea.setEditable(false);

        root.getChildren().add(this.logArea);

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    public void run() {
        launch();
    }

    public void update(String log) {
        Platform.runLater(() -> logArea.appendText(log));
    }
}
