package com.carousell.marketplace.cli;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

    public static ParsedInput parse(String line) {
        List<String> tokens = tokenize(line);
        if (tokens.isEmpty()) {
            return new ParsedInput("", new ArrayList<String>());
        }
        String commandName = tokens.get(0);
        List<String> args = new ArrayList<String>();
        for (int i = 1; i < tokens.size(); i++) {
            args.add(tokens.get(i));
        }
        return new ParsedInput(commandName, args);
    }

    private static List<String> tokenize(String line) {
        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '\'') {
                inQuotes = !inQuotes;
                current.append(ch); // keep quotes, service strips later
            } else if (ch == ' ') {
                if (inQuotes) {
                    current.append(ch);
                } else {
                    if (current.length() > 0) {
                        result.add(current.toString());
                        current.setLength(0);
                    }
                }
            } else {
                current.append(ch);
            }
        }
        if (current.length() > 0) {
            result.add(current.toString());
        }
        return result;
    }

    public static class ParsedInput {
        private final String commandName;
        private final List<String> args;

        public ParsedInput(String commandName, List<String> args) {
            this.commandName = commandName;
            this.args = args;
        }

        public String getCommandName() {
            return commandName;
        }

        public List<String> getArgs() {
            return args;
        }
    }
}
