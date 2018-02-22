package ftp_server.reply;

import java.util.ArrayList;
import java.util.List;

public class Reply {
    private Integer replyCode;
    private List<String> messageList;

    public Reply(Integer replyCode, List<String> messageList) {
        this.replyCode = replyCode;
        this.messageList = messageList;
    }

    public Integer getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(Integer replyCode) {
        this.replyCode = replyCode;
    }

    public void appendMessageList(String message) {
        this.messageList.add(message);
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public String getMessage() {
        if (this.messageList.size() == 1) {
            return getOneStringMessage();
        }
        return getMultiStringMessage();
    }

    private String getOneStringMessage() {
        return this.replyCode.toString() + " " + this.messageList.get(0) + "\r\n";
    }

    private String getMultiStringMessage() {
        StringBuilder result = new StringBuilder();
        List<String> subList = this.messageList.subList(0, this.messageList.size() - 1);
        for(String info : subList) {
            result.append(this.replyCode.toString()).append("-").append(info).append("\n");
        }
        result.append(this.replyCode.toString()).append(" ");
        result.append(this.messageList.get(this.messageList.size() - 1));
        result.append("\r\n");

        return result.toString();
    }

}
