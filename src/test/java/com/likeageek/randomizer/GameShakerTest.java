package com.likeageek.randomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;


public class GameShakerTest {
    private GameShaker gameShaker;

    @BeforeEach
    public void init() {
        gameShaker = new GameShaker(new FakeAsmFileManager());
        gameShaker.setSeed(3297392);
        gameShaker.initTowns();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ViridianCity", "VermilionCity", "CeruleanCity", "PewterCity", "CeladonCity", "FuchsiaCity", "SaffronCity", "CinnabarIsland"})
    public void shouldConvertAsmFileForATown(String town) throws URISyntaxException, IOException {
        gameShaker.setShuffledArenas(buildShuffledArenas());

        gameShaker.convertAsmFile(town);

        String expectedFileShuffled = new String(readAllBytes(get(getClass().getResource(town + "-shuffled.txt").toURI())));
        String asmFileShuffled = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-output/mapObjects/" + town + ".asm")));
        assertThat(asmFileShuffled).isEqualTo(expectedFileShuffled);
    }

    @Test
    public void shouldShuffleTownArenas() {
        gameShaker.shuffleArenas();

        Map<String, String> arenas = gameShaker.getShuffledArenas();
        assertThat(arenas.get("ViridianCity")).isEqualTo("CINNABAR_GYM");
        assertThat(arenas.get("VermilionCity")).isEqualTo("SAFFRON_GYM");
        assertThat(arenas.get("CeruleanCity")).isEqualTo("CERULEAN_GYM");
        assertThat(arenas.get("PewterCity")).isEqualTo("VIRIDIAN_GYM");
        assertThat(arenas.get("CeladonCity")).isEqualTo("VERMILION_GYM");
        assertThat(arenas.get("FuchsiaCity")).isEqualTo("CELADON_GYM");
        assertThat(arenas.get("SaffronCity")).isEqualTo("FUCHSIA_GYM");
        assertThat(arenas.get("CinnabarIsland")).isEqualTo("PEWTER_GYM");
    }

    private Map<String, String> buildShuffledArenas() {
        Map<String, String> arenas = new HashMap<>();
        arenas.put("ViridianCity", "CINNABAR_GYM");
        arenas.put("VermilionCity", "SAFFRON_GYM");
        arenas.put("CeruleanCity", "CERULEAN_GYM");
        arenas.put("PewterCity", "VIRIDIAN_GYM");
        arenas.put("CeladonCity", "VERMILION_GYM");
        arenas.put("FuchsiaCity", "CELADON_GYM");
        arenas.put("SaffronCity", "FUCHSIA_GYM");
        arenas.put("CinnabarIsland", "PEWTER_GYM");
        return arenas;
    }

    class FakeAsmFileManager implements IFileManager {

        @Override
        public void write(String townName, String asmSourceCode) throws IOException {
            FileWriter fileWriter = new FileWriter("/home/likeageek/Projects/randomizer-output/mapObjects/" + townName + ".asm");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(asmSourceCode);
            printWriter.close();
        }

        @Override
        public String read(String townName) throws IOException {
            return new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/mapObjects/" + townName + ".asm")));
        }
    }
}

