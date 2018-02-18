package logging;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LogWindow extends Application implements Initializable, Observer, Runnable {
    private TextArea logArea;
    private Thread thread;

    public LogWindow() {
        thread = new Thread(this);
    }

    public void initialize() {
        thread.start();
    }

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        this.logArea = new TextArea();
        this.logArea.setEditable(false);

        root.getChildren().add(this.logArea);

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea.textProperty().bind(Logger.getLog());
    }

    public void update(String info) {
        Logger.appendLog(info);
    }
}
