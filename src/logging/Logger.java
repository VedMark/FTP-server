package logging;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class Logger {
    private static StringProperty simpleLog = new SimpleStringProperty();
    private static String log;

    public static StringProperty getLog() { return simpleLog; }

    public static void appendLog(String info) {
        log += info + "\n";
        simpleLog.set(log);
    }
}
