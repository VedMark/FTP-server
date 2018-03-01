package ftp_server.server;

import ftp_server.command.*;

class CommandController {
    private DataTransferProcess serverDTP;

    CommandController(DataTransferProcess serverDTP) {
        this.serverDTP = serverDTP;
    }

    public Command createCommand(String message) {
        String cmd;
        String param = "";
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
        Command command;

        switch(cmd) {
            case "USER":  command = new USER(this.serverDTP, param);    break;
            case "PASS":  command = new PASS(this.serverDTP, param);    break;
            case "ACCT":  command = new ACCT(this.serverDTP, param);    break;
            case "CWD":   command = new CWD(this.serverDTP, param);     break;
            case "CDUP":  command = new CDUP(this.serverDTP);           break;
            case "SMNT":  command = new SMNT(this.serverDTP, param);    break;
            case "QUIT":  command = new QUIT();           break;
            case "REIN":  command = new REIN(this.serverDTP);           break;
            case "PORT":  command = new PORT(this.serverDTP, param);    break;
            case "PASV":  command = new PASV(this.serverDTP);           break;
            case "TYPE":  command = new TYPE(this.serverDTP, param);    break;
            case "STRU":  command = new STRU(this.serverDTP, param);    break;
            case "MODE":  command = new MODE(this.serverDTP, param);    break;
            case "RETR":  command = new RETR(this.serverDTP, param);    break;
            case "STOR":  command = new STOR(this.serverDTP, param);    break;
            case "STOU":  command = new STOU(this.serverDTP);           break;
            case "APPE":  command = new APPE(this.serverDTP, param);    break;
            case "ALLO":  command = new ALLO(this.serverDTP, param);    break;
            case "REST":  command = new REST(this.serverDTP, param);    break;
            case "RNFR":  command = new RNFR(this.serverDTP, param);    break;
            case "RNTO":  command = new RNTO(this.serverDTP, param);    break;
            case "ABOR":  command = new ABOR(this.serverDTP);           break;
            case "DELE":  command = new DELE(this.serverDTP, param);    break;
            case "RMD":   command = new RMD(this.serverDTP, param);     break;
            case "MKD":   command = new MKD(this.serverDTP, param);     break;
            case "PWD":   command = new PWD(this.serverDTP);            break;
            case "LIST":  command = new LIST(this.serverDTP, param);    break;
            case "NLST":  command = new NLST(this.serverDTP, param);    break;
            case "SITE":  command = new SITE(this.serverDTP, param);    break;
            case "SYST":  command = new SYST();                         break;
            case "STAT":  command = new STAT(this.serverDTP, param);    break;
            case "HELP":  command = new HELP(this.serverDTP, param);    break;
            case "NOOP":  command = new NOOP();           break;

            default:    command = new BadCommand();
        }

        return command;
    }
}
