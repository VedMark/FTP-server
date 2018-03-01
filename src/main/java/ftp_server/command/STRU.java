package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.DataTransferProcess;
import ftp_server.server.StructureEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class STRU implements Command {
    private static final String STRUCTURE_CODE_PATTERN = "[FRP]";
    private DataTransferProcess receiver;
    Reply reply;
    private String structure_code;

    public STRU(DataTransferProcess serverDTP, String structure_code) {
        this.receiver = serverDTP;
        this.structure_code = structure_code;
    }

    @Override
    public void execute() {
        if(receiver.getParameters().isAuthorized()) {
            if (checkWithRegex()) {

                processStructureParameter();

            } else {
                reply = new Reply(Reply.Code.CODE_501);
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private void processStructureParameter() {
        switch (structure_code) {
            case "F":
                receiver.getParameters().setStructure(StructureEnum.FILE);
                reply = new Reply(Reply.Code.CODE_200);
                break;
            case "R": reply = new Reply(Reply.Code.CODE_504);   break;
            case "P": reply = new Reply(Reply.Code.CODE_504);   break;
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
                "structure: " + receiver.getParameters().getMode().toString().toLowerCase()
        );
    }

    private Boolean checkWithRegex() {
        Pattern p = Pattern.compile(STRUCTURE_CODE_PATTERN);
        Matcher m = p.matcher(structure_code);
        return m.matches();
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}
