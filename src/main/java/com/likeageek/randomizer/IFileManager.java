package com.likeageek.randomizer;

import java.io.IOException;

public interface IFileManager {
    void write(String filePath, String asmSourceCode) throws IOException;

    String read(String filePath) throws IOException;
}
