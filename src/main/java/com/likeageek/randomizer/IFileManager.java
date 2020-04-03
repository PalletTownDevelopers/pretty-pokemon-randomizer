package com.likeageek.randomizer;

import java.io.IOException;

public interface IFileManager {
    void write(String filePath, String[] asmSourceCode);

    String[] read(String filePath);

    void copyGame() throws IOException;
}
