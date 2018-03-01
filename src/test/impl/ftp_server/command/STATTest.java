package ftp_server.command;

import ftp_server.server.DataTransferProcess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class STATTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd HH:mm", Locale.ENGLISH);

    private DataTransferProcess serverDTP;
    private File file;

    @BeforeEach
    void initServerDTP() {
        serverDTP = new DataTransferProcess();

        USER user = new USER(serverDTP, "admin");
        PASS pass = new PASS(serverDTP, "admin");
        user.execute();
        pass.execute();
        String ftp = "ftp";
        String path = serverDTP.getParameters().getHome() + "/" + ftp;
        file = new File(path);
    }

    @Test
    void execute_NotLoggedIn_Code530() throws UnexpectedCodeException {
        serverDTP.getParameters().reset();
        STAT stat = new STAT(serverDTP, "ftp");
        stat.execute();
        String response = stat.getResponseMessage();
        assertEquals("530 You are not logged in\r\n", response);
    }

    @Test
    void execute_SystemStat_Code211() throws UnexpectedCodeException {
        if(!file.mkdir()) {
            fail("Could not create file");
        }

        STAT stat = new STAT(serverDTP, "");
        stat.execute();
        String response = stat.getResponseMessage();
        assertEquals("211 ModeEnum: stream; TypeEnum: ascii; FormEnum: non-print; StructureEnum: file\r\n", response);
    }

    @Test
    void execute_FileStat_Code213() throws UnexpectedCodeException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            fail("Could not create file for testing");
        }

        STAT stat = new STAT(serverDTP, "ftp");
        stat.execute();
        String response = stat.getResponseMessage();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());

        assertEquals("213 -rw-   1 mark     mark            0 "
                + DATE_FORMAT.format(cal.getTime())
                + " ftp\r\n", response);
    }

    @Test
    void execute_DirStat_Code212() throws IOException {
        if(!file.mkdir()) {
            fail("Could not create file");
        }

        File file1 = new File(file.getCanonicalPath() + "/file");
        file1.createNewFile();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        String str1 = "drwx   1 mark     mark         4096 " + DATE_FORMAT.format(cal.getTime()) + " .\n";
        String str2 = "drwx   1 mark     mark         4096 " + DATE_FORMAT.format(cal.getTime()) + " ..\n";
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(file1.lastModified());
        String str3 = "-rw-   1 mark     mark            0 " + DATE_FORMAT.format(cal1.getTime()) + " file\n";

        STAT stat = new STAT(serverDTP, "ftp");
        stat.execute();
        String response = stat.getResponseMessage();
        file1.delete();
        assertEquals("212-" + str1 + str2 + str3 + "212\r\n", response);
    }

    @Test
    void execute_NoSuchFile_Code450() throws UnexpectedCodeException {
        if(!file.mkdir()) {
            fail("Could not create file");
        }

        STAT stat = new STAT(serverDTP, "ftp1");
        stat.execute();
        String response = stat.getResponseMessage();
        assertEquals("450 Requested file action not taken, file does not exists\r\n", response);
    }

    @AfterEach
    void deleteFile() {
        file.delete();
    }
}