package com.likeageek.randomizer;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.likeageek.randomizer.ConfigurationBuilder.configuration;
import static java.lang.Long.parseLong;

public class App {

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
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

                System.out.println("Initialize output directory\n");
                File outputDirectory = new File(configuration.getOutputDirectory());
                FileUtils.deleteDirectory(outputDirectory);
                outputDirectory.mkdirs();

                AsmFileManager asmFileManager = new AsmFileManager(configuration.getPokemonDirectory(), configuration.getOutputDirectory());
                AsmFileParser asmFileParser = new AsmFileParser();
                GameShaker gameShaker = new GameShaker(configuration, asmFileManager, asmFileParser);
                gameShaker.init();
                gameShaker.shake();

                System.out.println("Set Permission for output directory\n");
                outputDirectory.setExecutable(true);
                outputDirectory.setReadable(true);
                outputDirectory.setWritable(true);

                String makeArgs = "";
                if(cmd.hasOption("debug")) {
                    makeArgs += "DEBUG=1 ";
                }
                if(cmd.hasOption("sprite_yellow")) {
                    makeArgs += "SPRITE_YELLOW=1 ";
                }

                System.out.println("Compile the rom\n");
                runShellCommand(new String[]{"bash","-c","cd " + configuration.getOutputDirectory() + " && make red " + makeArgs});
            }
        }
    }

    private static Options buildCliOptions() {
        Options options = new Options();
        options.addOption("seed", true, "seed for this run");
        options.addOption("shake", false, "shake this game");
        options.addOption("pokemon_dir", true, "path to disassembled pokemon game");
        options.addOption("output_dir", true, "path to shuffled disassembled pokemon game");
        options.addOption("debug", false, "set the debug mode of pokemon");
        options.addOption("sprite_yellow", false, "Replace pokemon yellow sprite instead of pokemon red");
        return options;
    }

    private static void runShellCommand(String[] command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
            System.exit(0);
        }
    }
}
