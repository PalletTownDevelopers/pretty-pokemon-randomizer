package com.likeageek.randomizer;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void shouldDisplayShuffledArenas_whenRunGameShaker() throws ParseException, IOException {
        PrintStream printStream = new PrintStream("out.log");
        System.setOut(printStream);
        App.main(new String[]{"-shake", "-seed", "424242", "-pokemon_dir", "/home/likeageek/Projects/randomizer-cache/", "-output_dir", "/home/likeageek/Projects/randomizer-output/"});
        String consoleOutput = new String(readAllBytes(get("out.log")));
        assertThat(consoleOutput).contains("gameshaker for pokemonredblue");
    }

}