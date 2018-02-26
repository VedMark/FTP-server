package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.server.Form;
import ftp_server.server.Mode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MODE implements Command {
    private static final String MODE_CODE_PATTERN = "[SBC]";

    private FTPServerDTP receiver;
    Reply reply;
    private String mode_code;

    public MODE(FTPServerDTP serverDTP, String mode_code) {
        this.receiver = serverDTP;
        this.mode_code = mode_code.trim();
    }

    @Override
    public void execute() {
        if(receiver.getParameters().isAuthorized()) {
            if (checkWithRegex()) {

                processModeParameter();

            } else {
                reply = new Reply(Reply.Code.CODE_501);
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private void processModeParameter() {
        switch (mode_code) {
            case "S":
                receiver.getParameters().setMode(Mode.Stream);
                reply = new Reply(Reply.Code.CODE_200);
                break;
            case "B": reply = new Reply(Reply.Code.CODE_504);   break;
            case "C": reply = new Reply(Reply.Code.CODE_504);   break;
        }
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_200 == this.reply.getReplyCode()) {
            message = getCode200FormattedString();
        } else if(Reply.Code.CODE_504 == this.reply.getReplyCode()) {
            message = reply.getMessage();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode200FormattedString() {
        return String.format(
                this.reply.getMessage(),
                "mode: " + receiver.getParameters().getMode().toString().toLowerCase()
        );
    }

    private Boolean checkWithRegex() {
        Pattern p = Pattern.compile(MODE_CODE_PATTERN);
        Matcher m = p.matcher(mode_code);
        return m.matches();
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
