package ftp_server.view;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class LogComponent implements View{
    private TextArea logArea = new TextArea();

    public LogComponent() {
        this.logArea.setEditable(false);
    }

    public TextArea getLogArea() {
        return logArea;
    }

    @Override
    public void update(String info) {
        final String text = info;
        System.out.println(text);
        Platform.runLater(() -> this.logArea.appendText(text));
    }
}
