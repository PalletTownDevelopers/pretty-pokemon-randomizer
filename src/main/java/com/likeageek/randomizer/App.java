package com.likeageek.randomizer;

import org.apache.commons.cli.*;

import java.io.IOException;

import static com.likeageek.randomizer.ConfigurationBuilder.configuration;
import static java.lang.Long.parseLong;

public class App {

    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("gameshaker for pokemonredblue");
        Options options = buildCliOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("shake")) {
            System.out.println("shake that!");
            if (cmd.hasOption("seed")) {
                Configuration configuration = configuration()
                        .seed(parseLong(cmd.getOptionValue("seed")))
                        .pokemonDirectory(cmd.getOptionValue("pokemon_dir"))
                        .outputDirectory(cmd.getOptionValue("output_dir"))
                        .build();
                AsmFileManager asmFileManager = new AsmFileManager(configuration.getPokemonDirectory(), configuration.getOutputDirectory());
                AsmFileParser asmFileParser = new AsmFileParser();
                GameShaker gameShaker = new GameShaker(configuration, asmFileManager, asmFileParser);
                gameShaker.init();
                gameShaker.shake();
            }
        }
    }

    private static Options buildCliOptions() {
        Options options = new Options();
        options.addOption("seed", true, "seed for this run");
        options.addOption("shake", false, "shake this game");
        options.addOption("pokemon_dir", true, "path to disassembled pokemon game");
        options.addOption("output_dir", true, "path to shuffled disassembled pokemon game");
        return options;
    }
}
