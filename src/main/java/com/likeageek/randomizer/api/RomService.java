package com.likeageek.randomizer.api;

import com.likeageek.randomizer.AsmFileManager;
import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.Configuration;
import com.likeageek.randomizer.GameShaker;
import org.apache.commons.io.FileUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.likeageek.randomizer.ConfigurationBuilder.configuration;

@Singleton
public class RomService {
    @Produces
    @ApplicationScoped //TODO
    RomRepository repository;

    @Produces
    @ApplicationScoped //TODO
    AsmFileManager asmFileManager;

    @Produces
    @ApplicationScoped //TODO
    AsmFileParser asmFileParser;

    public File generate(RomInformation romInformation) throws IOException, InterruptedException {
        System.out.println("gameshaker for pokemonredblue");
        Configuration configuration = configuration()
                .seed(romInformation.getSeed())
                .pokemonDirectory("/tmp/pokered/")
                .outputDirectory("/tmp/randomizer-output_" + "timestamp") //FIXME
                .build();

        System.out.println("Initialize output directory\n");
        File outputDirectory = new File(configuration.getOutputDirectory());
        outputDirectory.mkdirs();

        asmFileManager = new AsmFileManager(configuration.getPokemonDirectory(), configuration.getOutputDirectory());
        asmFileParser = new AsmFileParser();
        GameShaker gameShaker = new GameShaker(configuration, asmFileManager, asmFileParser);
        gameShaker.init();
        gameShaker.shake();

        System.out.println("Set Permission for output directory\n");
        outputDirectory.setExecutable(true);
        outputDirectory.setReadable(true);
        outputDirectory.setWritable(true);

        String makeArgs = "";
        /*if (cmd.hasOption("debug")) {
            makeArgs += "DEBUG=1 ";
        }
        if (cmd.hasOption("sprite_yellow")) {
            makeArgs += "SPRITE_YELLOW=1 ";
        }*/

        System.out.println("Compile the rom\n");
        runShellCommand(new String[]{"bash", "-c", "cd " + configuration.getOutputDirectory() + " && make red " + makeArgs});


        System.out.println("Move rom file to public folder and delete output directory\n");
        File romGenerated = new File(configuration.getOutputDirectory() + "/pokered.gbc");
        FileUtils.copyFile(romGenerated, new File("/public/pokered_" + "params.timestamp" + ".gbc"));
        FileUtils.deleteDirectory(new File(configuration.getOutputDirectory()));
        return romGenerated;
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

    @Transactional
    public void saveInformation(RomInformation romInformation) {
        Rom entity = new Rom(romInformation.getSeed(), romInformation);
        repository.persist(entity);
    }
}
