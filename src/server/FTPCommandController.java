package server;

import command.PWD;
import command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FTPCommandController {
    Command lastCommand = null;

    class CommandChunks {
        private String cmd = "";
        private List<String> params = new ArrayList<>();
    }

    public static Command createRequest(String message) {
        return null;
    }

    private static void parse(String message) {

    }
}
