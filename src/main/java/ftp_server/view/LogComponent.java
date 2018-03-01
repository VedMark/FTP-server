package ftp_server.view;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LogComponent implements View{
    private TextArea logArea = new TextArea();

    public LogComponent() {
        this.logArea.setEditable(false);
        this.logArea.setFont(Font.font("Helvetica", FontWeight.NORMAL, 12));
    }

    public TextArea getLogArea() {
        return logArea;
    }

    @Override
    public void update(String info) {
        final String text = info;
        Platform.runLater(() -> this.logArea.appendText(text));
    }
}
