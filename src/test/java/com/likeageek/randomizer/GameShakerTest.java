package com.likeageek.randomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;

import static com.likeageek.randomizer.ConfigurationBuilder.configuration;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;

class GameShakerTest {

    private GameShaker gameShaker;

    @BeforeEach
    void setUp() throws IOException {
        PrintStream printStream = new PrintStream("out.log");
        System.setOut(printStream);

        Configuration configuration = configuration()
                .seed(424242)
                .pokemonDirectory("../randomizer-output/")
                .outputDirectory("../randomizer-output/")
                .build();
        gameShaker = new GameShaker(configuration, new FakeAsmFileManager(), new AsmFileParser());
        gameShaker.init();
    }

    @Test
    void shouldInit() throws IOException {
        gameShaker.init();

        String consoleOutput = new String(readAllBytes(get("out.log")));
        assertThat(consoleOutput).contains("gym shuffler");
    }

    @Test
    void shouldShakeAndDisplayShuffler() throws IOException {
        gameShaker.shake();

        String consoleOutput = new String(readAllBytes(get("out.log")));
        assertThat(consoleOutput).contains("gym shuffler");
    }
}
