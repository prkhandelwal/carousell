package com.carousell.marketplace.cli;

import com.carousell.marketplace.repository.*;
import com.carousell.marketplace.service.DefaultMarketplaceService;
import com.carousell.marketplace.service.MarketplaceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

    public static void main(String[] args) throws IOException {

        UserRepository userRepository = new InMemoryUserRepository();
        ListingRepository listingRepository = new InMemoryListingRepository();
        CategoryStatsRepository categoryStatsRepository = new InMemoryCategoryStatsRepository();

        MarketplaceService service = new DefaultMarketplaceService(
                userRepository,
                listingRepository,
                categoryStatsRepository
        );

        CommandFactory commandFactory = new CommandFactory();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        while (true) {
            System.out.print("# ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            try {
                InputParser.ParsedInput parsed = InputParser.parse(line);
                Command command = commandFactory.create(parsed.getCommandName(), parsed.getArgs());
                CommandContext context = new CommandContext(service, System.out,
                        parsed.getCommandName(), parsed.getArgs());
                command.execute(context);
            } catch (Exception e) {
                System.out.println("Error - internal error");
            }
        }
    }
}
