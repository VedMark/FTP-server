package ftp_server.server;

class SyntaxErrorException extends Exception {
    SyntaxErrorException() {
        super("Syntax error. Command unrecognized.\n");
    }
}
