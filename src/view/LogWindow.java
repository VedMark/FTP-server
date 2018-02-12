package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class LogWindow extends Application implements Observer {
    private TextArea logArea;

    public void initialize() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();

        logArea = new TextArea();
        logArea.setEditable(false);

        root.getChildren().add(logArea);

        primaryStage.setTitle("FTP server");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    public void update(String log) {
        logArea.appendText(log);
    }
}
