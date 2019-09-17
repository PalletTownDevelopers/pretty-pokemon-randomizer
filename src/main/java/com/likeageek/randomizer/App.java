package com.likeageek.randomizer;

import org.apache.commons.cli.*;

import static java.lang.Integer.parseInt;

public class App {

    public static void main(String[] args) throws ParseException {
        System.out.println("gameshaker for pokemonredblue");
        Options options = new Options();
        options.addOption("seed", true, "seed for this run");
        options.addOption("shake", false, "shake this game");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("shake")) {
            System.out.println("shake that!");
            GameShaker gameShaker = new GameShaker();
            if (cmd.hasOption("seed")) {
                String seed = cmd.getOptionValue("seed");
                gameShaker.setSeed(parseInt(seed));
            }
            gameShaker.shuffleArenas();
            gameShaker.getShuffledArenas().values().stream().forEach(s -> {
                System.out.println(s + "\r\n");
            });
        }
    }

}
