package server;

import command.PWD;
import command.Request;

class RequestController {
    private FTPParser parser;

    public Request createRequest(String request) {
        parser.parse(request);
        return new PWD();
    }
}
