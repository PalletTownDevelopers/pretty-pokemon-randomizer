package com.likeageek.randomizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.join;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.copyDirectory;


public class AsmFileManager implements IFileManager {
    private static final String SAVE_FILE_SUFFIX = ".asm";
    private static final String SPLITTER_REGEXP = "\n\t";
    private String pokemonPath;
    private String outputPath;

    AsmFileManager(String pokemonPath, String outputPath) {
        this.pokemonPath = pokemonPath;
        this.outputPath = outputPath;
    }

    public void write(String filePath, String[] asmSourceCode) {
        try {
            FileWriter fileWriter = new FileWriter(outputPath + filePath + SAVE_FILE_SUFFIX);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(join(SPLITTER_REGEXP, asmSourceCode));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] read(String filePath) {
        String[] fileContent = new String[0];
        try {
            fileContent = new String(readAllBytes(get(pokemonPath + filePath + ".asm"))).split(SPLITTER_REGEXP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public void copyGame() throws IOException {
        copyDirectory(new File(pokemonPath), new File(outputPath));
    }

}
