package ftp_server.reply;

import java.util.EnumMap;
import java.util.Map;

public class Reply {
    private static final Map<Code, String> map = new EnumMap<>(Code.class);

    private Code replyCode;

    public enum Code {
        CODE_110, CODE_120, CODE_125,
        CODE_150, CODE_200, CODE_202,
        CODE_211, CODE_212, CODE_213,
        CODE_214, CODE_215, CODE_220,
        CODE_221, CODE_225, CODE_226,
        CODE_227, CODE_230, CODE_250,
        CODE_257, CODE_331, CODE_332,
        CODE_350, CODE_421, CODE_425,
        CODE_426, CODE_450, CODE_451,
        CODE_452, CODE_500, CODE_501,
        CODE_502, CODE_503, CODE_504,
        CODE_530, CODE_532, CODE_550,
        CODE_551, CODE_552, CODE_553;
    }

    static {
        map.put(Code.CODE_110, "110 Restart marker reply\r\n");
        map.put(Code.CODE_120, "120 Service ready in %s minutes\r\n");
        map.put(Code.CODE_125, "125 Data connection already open; transfer starting\r\n");
        map.put(Code.CODE_150, "150 %s\r\n");
        map.put(Code.CODE_200, "200 Command okay, %s\r\n");
        map.put(Code.CODE_202, "202 Command not implemented, superfluous at this site\r\n");
        map.put(Code.CODE_211, "211 %s\r\n");
        map.put(Code.CODE_212, "212-%s212\r\n");
        map.put(Code.CODE_213, "213 %s\r\n");
        map.put(Code.CODE_214, "214 %s\r\n");
        map.put(Code.CODE_215, "215 %s\r\n");
        map.put(Code.CODE_220, "220-----------Welcome to FTP-server----------\n" +
                "220-You will be disconnected after %d minute%s of inactivity\n" +
                "220 Service ready\r\n");
        map.put(Code.CODE_221, "221-Goodbye\n" +
                "221 Logout\r\n");
        map.put(Code.CODE_225, "225 Data connection open; no transfer in progress\r\n");
        map.put(Code.CODE_226, "226 %s\r\n");
        map.put(Code.CODE_227, "227 Entering Passive Mode(%s,%s,%s,%s,%s,%s)\r\n");
        map.put(Code.CODE_230, "230 User logged in, proceed\r\n");
        map.put(Code.CODE_250, "250 Requested file action okay\r\n");
        map.put(Code.CODE_257, "257 Your current location is %s\r\n");
        map.put(Code.CODE_331, "331 User %s okay, need password\r\n");
        map.put(Code.CODE_332, "332 Need account for login\r\n");
        map.put(Code.CODE_350, "350 Requested file action pending further information\r\n");
        map.put(Code.CODE_421, "421 Service not available, closing control connection\r\n");
        map.put(Code.CODE_425, "425 Can't open data connection\r\n");
        map.put(Code.CODE_426, "426 Connection closed; transfer aborted\r\n");
        map.put(Code.CODE_450, "450 Requested file action not taken, %s\r\n");
        map.put(Code.CODE_451, "451 Requested action aborted: local error in processing\r\n");
        map.put(Code.CODE_452, "452 Requested action not taken, insufficient storage space in system.\r\n");
        map.put(Code.CODE_500, "500 Syntax error, command unrecognized\r\n");
        map.put(Code.CODE_501, "501 Syntax error in parameters or arguments\r\n");
        map.put(Code.CODE_502, "502 Command not implemented\r\n");
        map.put(Code.CODE_503, "503 Bad sequence of commands\r\n");
        map.put(Code.CODE_504, "504 Command not implemented for that parameter\r\n");
        map.put(Code.CODE_530, "530 %s\r\n");
        map.put(Code.CODE_532, "532 Need account for storing files\r\n");
        map.put(Code.CODE_550, "550 Requested action not taken, %s\r\n");
        map.put(Code.CODE_551, "551 Requested action aborted: page type unknown\r\n");
        map.put(Code.CODE_552, "552 Requested file action aborted, exceeded storage allocation\r\n");
        map.put(Code.CODE_553, "553 Requested action not taken, %s\r\n");
    }

    public Reply(Code replyCode) {
        this.replyCode = replyCode;
    }

    public Code getReplyCode() {
        return replyCode;
    }

    public String getMessage() {
        return map.get(this.replyCode);
    }
}
