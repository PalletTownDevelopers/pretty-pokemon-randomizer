package com.likeageek.randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class AsmFileManager implements IFileManager {
    private static final String SAVE_FILE_SUFFIX = ".asm";
    private static final String TOWNS = "mapObjects/";
    private String pokemonPath;
    private String outputPath;

    public AsmFileManager(String pokemonPath, String outputPath) {
        this.pokemonPath = pokemonPath;
        this.outputPath = outputPath;
    }

    public void write(String townName, String asmSourceCode) throws IOException {
        FileWriter fileWriter = new FileWriter(outputPath + TOWNS + townName + SAVE_FILE_SUFFIX);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(asmSourceCode);
        printWriter.close();
    }

    public String read(String townName) throws IOException {
        return new String(readAllBytes(get(pokemonPath + TOWNS + townName + ".asm")));
    }

}
