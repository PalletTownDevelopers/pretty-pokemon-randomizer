package com.likeageek.randomizer;

import java.io.IOException;

public interface IFileManager {
    void write(String townName, String asmSourceCode) throws IOException;
    String read(String townName) throws IOException;
}
