package com.likeageek.randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class FakeAsmFileManager implements IFileManager {

    @Override
    public void write(String filePath, String asmSourceCode) throws IOException {
        FileWriter fileWriter = new FileWriter("/home/likeageek/Projects/randomizer-output/" + filePath + ".asm");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(asmSourceCode);
        printWriter.close();
    }

    @Override
    public String[] read(String filePath) throws IOException {
        return new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/" + filePath + ".asm"))).split("\n\t");
    }

    @Override
    public void copyGame() {

    }
}
