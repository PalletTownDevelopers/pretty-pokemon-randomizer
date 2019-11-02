package com.likeageek.randomizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.copyDirectory;


public class AsmFileManager implements IFileManager {
    private static final String SAVE_FILE_SUFFIX = ".asm";
    private String pokemonPath;
    private String outputPath;

    public AsmFileManager(String pokemonPath, String outputPath) {
        this.pokemonPath = pokemonPath;
        this.outputPath = outputPath;
    }

    public void write(String filePath, String asmSourceCode) throws IOException {
        FileWriter fileWriter = new FileWriter(outputPath + filePath + SAVE_FILE_SUFFIX);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(asmSourceCode);
        printWriter.close();
    }

    public String read(String filePath) throws IOException {
        return new String(readAllBytes(get(pokemonPath + filePath + ".asm")));
    }

    public void copyGame() throws IOException {
        copyDirectory(new File(pokemonPath), new File(outputPath));
    }

}
