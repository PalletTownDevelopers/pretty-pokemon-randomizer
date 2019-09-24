package com.likeageek.randomizer;

import org.apache.commons.cli.*;

import java.io.IOException;

import static com.likeageek.randomizer.ConfigurationBuilder.configuration;
import static java.lang.Integer.parseInt;

public class App {

    public static void main(String[] args) throws ParseException {
        System.out.println("gameshaker for pokemonredblue");
        Options options = buildCliOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("shake")) {
            System.out.println("shake that!");
            if (cmd.hasOption("seed")) {
                Configuration configuration = configuration()
                        .seed(parseInt(cmd.getOptionValue("seed")))
                        .pokemonPath(cmd.getOptionValue("pokemon_path"))
                        .outputPath(cmd.getOptionValue("output_path"))
                        .build();
                launch(configuration);
            }
        }
    }

    private static Options buildCliOptions() {
        Options options = new Options();
        options.addOption("seed", true, "seed for this run");
        options.addOption("shake", false, "shake this game");
        options.addOption("pokemon_path", true, "path to disassembled pokemon game");
        options.addOption("output_path", true, "path to shuffled disassembled pokemon game");
        return options;
    }

    private static void launch(Configuration configuration) {
        GameShaker gameShaker = new GameShaker(new AsmFileManager(configuration.getPokemonPath(), configuration.getOutputPath()));
        gameShaker.setSeed(configuration.getSeed());
        gameShaker.shuffleArenas();
        gameShaker.getShuffledArenas().keySet().forEach(town -> {
            try {
                gameShaker.convertAsmFile(town);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gameShaker.getShuffledArenas().values().forEach(arena -> {
            System.out.println(arena + "\r\n");
        });
    }
}
