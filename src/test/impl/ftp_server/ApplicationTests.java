package ftp_server;

import ftp_server.view.FXServerApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class ApplicationTests {

    private static Thread thread;

    @BeforeAll
    static void initAll() {
        thread = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                try {
                    FXServerApp.start();
                }
                catch (Exception ignored) {  }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @AfterAll
    static void tearDownAll() {
        thread.interrupt();
    }
}
