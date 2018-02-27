package ftp_server.command;

import ftp_server.reply.Reply;
import ftp_server.server.FTPServerDTP;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PORT implements Command {
    private static final String ADDRESS_PATTERN =
            "(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])(,(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])){5}";
    private FTPServerDTP receiver;
    private Reply reply;
    private String host_port;

    public PORT(FTPServerDTP serverDTP, String host_port) {
        this.receiver = serverDTP;
        this.host_port = host_port;
    }

    @Override
    public void execute() {
        if(this.receiver.getParameters().isAuthorized()) {

            if(checkWithRegex()) {
                String[] arr = host_port.split(",");
                String addr = arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3];
                Integer port = (Integer.parseInt(arr[4]) << 8) + Integer.parseInt(arr[5]);

                receiver.getParameters().toActiveProcess(new InetSocketAddress(addr, port));
                reply = new Reply(Reply.Code.CODE_200);
            } else {
                reply = new Reply(Reply.Code.CODE_501);
            }
        } else {
            reply = new Reply(Reply.Code.CODE_530);
        }
    }

    private Boolean checkWithRegex() {
        Pattern p = Pattern.compile(ADDRESS_PATTERN);
        Matcher m = p.matcher(host_port);
        return m.matches();
    }

    @Override
    public String getResponseMessage() throws UnexpectedCodeException {
        String message;
        if(Reply.Code.CODE_200 == this.reply.getReplyCode()) {
            message = getCode200FormattedString();
        } else if(Reply.Code.CODE_501 == this.reply.getReplyCode()) {
            message = this.reply.getMessage();
        } else if(Reply.Code.CODE_530 == this.reply.getReplyCode()) {
            message = getCode530FormattedString();
        } else {
            throw new UnexpectedCodeException();
        }

        return message;
    }

    private String getCode200FormattedString() {
        return String.format(this.reply.getMessage(), "entering Active Mode");
    }

    private String getCode530FormattedString() {
        return String.format(this.reply.getMessage(), NOT_AUTHENTICATED_MESSAGE);
    }
}

