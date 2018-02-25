package ftp_server.view;

import ftp_server.ApplicationTests;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogComponentTest extends ApplicationTests {
    LogComponent logComponent;

    @BeforeEach
    void initLogComponent() {
        this.logComponent = new LogComponent();
    }

    @Test
    void getLogArea() {
        TextArea logArea = this.logComponent.getLogArea();
        assertNotNull(logArea);
    }

    @Test
    void update() {
        TextArea logArea = this.logComponent.getLogArea();
        assertNotNull(logArea);
    }
}