package com.likeageek.randomizer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AsmFileManagerTest {
    private AsmFileManager asmFileManager = new AsmFileManager("/home/likeageek/Projects/randomizer-cache/", "/home/likeageek/Projects/randomizer-output/");

    @Test
    public void shouldCopyPokemonFilesToOutputPath() throws IOException {
        asmFileManager.copyGame();
        try (Stream<Path> files = Files.list(Paths.get("/home/likeageek/Projects/randomizer-output/"))) {
            assertThat(files.count()).isEqualTo(46l);
        }
    }
}