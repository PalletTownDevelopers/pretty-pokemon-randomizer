package com.likeageek.randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.join;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class FakeAsmFileManager implements IFileManager {

    @Override
    public void write(String filePath, String[] asmSourceCode) {
        try {
            FileWriter fileWriter = new FileWriter("/home/likeageek/Projects/randomizer-output/" + filePath + ".asm");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(join("\n\t", asmSourceCode));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String[] read(String filePath) {
        String[] fileContent = new String[0];
        try {
            fileContent = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/" + filePath + ".asm"))).split("\n\t");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    @Override
    public void copyGame() {

    }
}
