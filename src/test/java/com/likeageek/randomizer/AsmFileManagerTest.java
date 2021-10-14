package com.likeageek.randomizer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AsmFileManagerTest {

    @Test
    void shouldCopyPokemonFilesToOutputPath() throws IOException {
        AsmFileManager asmFileManager = new AsmFileManager("../randomizer-pokered", "../randomizer-output/");

        asmFileManager.copyGame();

        try (Stream<Path> files = Files.list(Paths.get("../randomizer-output/"))) {
            assertThat(files.count()).isEqualTo(46l);
        }
    }
}
