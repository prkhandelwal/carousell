package com.carousell.marketplace.cli;

import com.carousell.marketplace.service.MarketplaceService;

import java.io.PrintStream;
import java.util.List;

public class CommandContext {

    private final MarketplaceService service;
    private final PrintStream out;
    private final String commandName;
    private final List<String> args;

    public CommandContext(MarketplaceService service,
                          PrintStream out,
                          String commandName,
                          List<String> args) {
        this.service = service;
        this.out = out;
        this.commandName = commandName;
        this.args = args;
    }

    public MarketplaceService getService() {
        return service;
    }

    public PrintStream getOut() {
        return out;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArgs() {
        return args;
    }
}
