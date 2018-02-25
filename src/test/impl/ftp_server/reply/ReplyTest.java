package ftp_server.reply;

import ftp_server.ApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReplyTest extends ApplicationTests {
    private Reply reply;

    @BeforeEach
    void initLogComponent() {
        this.reply = new Reply(Reply.Code.CODE_110);
    }

    @Test
    void getReplyCode() {
        assertEquals(Reply.Code.CODE_110, this.reply.getReplyCode());
    }

    @Test
    void getMessage() {
        assertEquals("110 Restart marker reply\r\n", this.reply.getMessage());
    }
}