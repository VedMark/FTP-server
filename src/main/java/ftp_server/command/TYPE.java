package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;
import ftp_server.server.Form;
import ftp_server.server.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TYPE implements Command {
    private static final String TYPE_CODE_PATTERN =
            "A *[NTC]?|E *[NTC]?|I|L *(25[0-6]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[1-9])";

    private FTPServerDTP receiver;
    private Reply reply;
    private String type_code;

    public TYPE(FTPServerDTP serverDTP, String type_code) {
        this.receiver = serverDTP;
        this.type_code = type_code.trim();
    }

    @Override
    public void execute() {
        if(receiver.getParameters().isAuthorized()) {
            if (checkWithRegex()) {

                String[] arr = type_code.split(" ");
                processTypeParameter(arr);

            } else {
                reply = new Reply(Reply.Code.CODE_501);
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private void processTypeParameter(String[] arr) {
        switch (arr[0]) {
            case "A": {
                if (arr.length == 2) {
                    processFormParameter(arr[1]);
                } else {
                    receiver.getParameters().setType(Type.ASCII);
                    reply = new Reply(Reply.Code.CODE_200);
                }
            }
            break;
            case "E":
                reply = new Reply(Reply.Code.CODE_504);
                break;
            case "I":
                receiver.getParameters().setType(Type.IMAGE);
                reply = new Reply(Reply.Code.CODE_200);
                break;
            case "L":
                reply = new Reply(Reply.Code.CODE_504);
                break;
        }
    }

    private void processFormParameter(String s) {
        switch (s) {
            case "N":
                receiver.getParameters().setForm(Form.NON_PRINT);
                reply = new Reply(Reply.Code.CODE_200);
                break;
            case "T": reply = new Reply(Reply.Code.CODE_504);   break;
            case "C": reply = new Reply(Reply.Code.CODE_504);   break;
        }
    }

    private Boolean checkWithRegex() {
        Pattern p = Pattern.compile(TYPE_CODE_PATTERN);
        Matcher m = p.matcher(type_code);
        return m.matches();
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_200 == this.reply.getReplyCode()) {
            message = getCode200FormattedString();
        } else if(Reply.Code.CODE_501 == this.reply.getReplyCode()) {
            message = reply.getMessage();
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
                "type: " + receiver.getParameters().getType().toString().toLowerCase()
        );
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
