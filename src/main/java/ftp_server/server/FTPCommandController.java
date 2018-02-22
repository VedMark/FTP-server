package ftp_server.server;

import ftp_server.command.*;
import java.lang.UnsupportedOperationException;

import java.util.HashMap;
import java.util.Map;

class FTPCommandController {
    private Map<CommandType, Command> commandsMap = new HashMap<>();

    FTPCommandController(FTPServerDTP serverDTP) {
        this.commandsMap.put(CommandType.USER,  new USER(serverDTP));
        this.commandsMap.put(CommandType.PASS,  new PASS(serverDTP));
        this.commandsMap.put(CommandType.ACCT,  new ACCT(serverDTP));
        this.commandsMap.put(CommandType.CWD,   new CWD(serverDTP));
        this.commandsMap.put(CommandType.CDUP,  new CDUP(serverDTP));
        this.commandsMap.put(CommandType.SMNT,  new SMNT(serverDTP));
        this.commandsMap.put(CommandType.QUIT,  new QUIT(serverDTP));
        this.commandsMap.put(CommandType.REIN,  new REIN(serverDTP));
        this.commandsMap.put(CommandType.PORT,  new PORT(serverDTP));
        this.commandsMap.put(CommandType.PASV,  new PASV(serverDTP));
        this.commandsMap.put(CommandType.TYPE,  new TYPE(serverDTP));
        this.commandsMap.put(CommandType.STRU,  new STRU(serverDTP));
        this.commandsMap.put(CommandType.MODE,  new MODE(serverDTP));
        this.commandsMap.put(CommandType.RETR,  new RETR(serverDTP));
        this.commandsMap.put(CommandType.STOR,  new STOR(serverDTP));
        this.commandsMap.put(CommandType.STOU,  new STOU(serverDTP));
        this.commandsMap.put(CommandType.APPE,  new APPE(serverDTP));
        this.commandsMap.put(CommandType.ALLO,  new ALLO(serverDTP));
        this.commandsMap.put(CommandType.REST,  new REST(serverDTP));
        this.commandsMap.put(CommandType.RNFR,  new RNFR(serverDTP));
        this.commandsMap.put(CommandType.RNTO,  new RNTO(serverDTP));
        this.commandsMap.put(CommandType.ABOR,  new ABOR(serverDTP));
        this.commandsMap.put(CommandType.DELE,  new DELE(serverDTP));
        this.commandsMap.put(CommandType.RMD,   new RMD(serverDTP));
        this.commandsMap.put(CommandType.MKD,   new MKD(serverDTP));
        this.commandsMap.put(CommandType.PWD,   new PWD(serverDTP));
        this.commandsMap.put(CommandType.LIST,  new LIST(serverDTP));
        this.commandsMap.put(CommandType.NLST,  new NLST(serverDTP));
        this.commandsMap.put(CommandType.SITE,  new SITE(serverDTP));
        this.commandsMap.put(CommandType.SYST,  new SYST(serverDTP));
        this.commandsMap.put(CommandType.STAT,  new STAT(serverDTP));
        this.commandsMap.put(CommandType.HELP,  new HELP(serverDTP));
        this.commandsMap.put(CommandType.NOOP,  new NOOP(serverDTP));
    }

    public Command createCommand(String message) {
        String cmd;
        String param = null;
        if(message.indexOf(' ') != -1) {
            cmd = message.substring(0, message.indexOf(' '));
            param = message.substring(message.indexOf(' ') + 1);
        }
        else {
            cmd = message;
        }
        cmd = cmd.toUpperCase();
        return newCommand(cmd, param);
    }

    private Command newCommand(String cmd, String param) {
        Command command = this.commandsMap.get(CommandType.valueOf(cmd));
        try {
            command.setParam(param);
        }
        catch (UnsupportedOperationException ignored) {
        }

        return command;
    }
}
