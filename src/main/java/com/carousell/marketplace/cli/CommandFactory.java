package com.carousell.marketplace.cli;

import java.util.List;

public class CommandFactory {

    public Command create(final String commandName, final List<String> args) {

        if ("REGISTER".equalsIgnoreCase(commandName)) {
            if (args.size() != 1) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String result = context.getService()
                            .registerUser(username);
                    context.getOut().println(result);
                }
            };
        }

        if ("CREATE_LISTING".equalsIgnoreCase(commandName)) {
            if (args.size() != 5) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String title = args.get(1);
                    String description = args.get(2);
                    String price = args.get(3);
                    String category = args.get(4);

                    String result = context.getService()
                            .createListing(username, title, description, price, category);
                    context.getOut().println(result);
                }
            };
        }

        if ("GET_LISTING".equalsIgnoreCase(commandName)) {
            if (args.size() != 2) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String listingId = args.get(1);

                    String result = context.getService()
                            .getListing(username, listingId);
                    context.getOut().println(result);
                }
            };
        }

        if ("DELETE_LISTING".equalsIgnoreCase(commandName)) {
            if (args.size() != 2) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String listingId = args.get(1);

                    String result = context.getService()
                            .deleteListing(username, listingId);
                    context.getOut().println(result);
                }
            };
        }

        if ("GET_CATEGORY".equalsIgnoreCase(commandName)) {
            if (args.size() != 4) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String category = args.get(1);
                    String sortBy = args.get(2);
                    String order = args.get(3);

                    java.util.List<String> lines = context.getService()
                            .getCategoryListings(username, category, sortBy, order);
                    for (String line : lines) {
                        context.getOut().println(line);
                    }
                }
            };
        }

        if ("GET_TOP_CATEGORY".equalsIgnoreCase(commandName)) {
            if (args.size() != 1) {
                return invalidArgumentsCommand();
            }
            return new Command() {
                @Override
                public void execute(CommandContext context) {
                    String username = args.get(0);
                    String result = context.getService()
                            .getTopCategory(username);
                    context.getOut().println(result);
                }
            };
        }

        // Unknown command
        return new Command() {
            @Override
            public void execute(CommandContext context) {
                context.getOut().println("Error - unknown command");
            }
        };
    }

    private Command invalidArgumentsCommand() {
        return new Command() {
            @Override
            public void execute(CommandContext context) {
                context.getOut().println("Error - invalid arguments");
            }
        };
    }
}
