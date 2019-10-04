package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.IFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;


public class ArenaShufflerTest {
    private ArenaShuffler arenaShuffler;

    @BeforeEach
    public void init() {
        arenaShuffler = new ArenaShuffler(new FakeAsmFileManager());
    }

    @Test
    public void shouldConvertAsmFileForATown() throws URISyntaxException, IOException {
        Map<String, String> arenas = new HashMap<>();
        arenas.put("ViridianCity", "CINNABAR_GYM");
        arenas.put("VermilionCity", "SAFFRON_GYM");
        arenas.put("CeruleanCity", "CERULEAN_GYM");
        arenas.put("PewterCity", "VIRIDIAN_GYM");
        arenas.put("CeladonCity", "VERMILION_GYM");
        arenas.put("FuchsiaCity", "CELADON_GYM");
        arenas.put("SaffronCity", "FUCHSIA_GYM");
        arenas.put("CinnabarIsland", "PEWTER_GYM");

        arenaShuffler.process(arenas);

        for (Map.Entry<String, String> entry : arenaShuffler.getShuffledArenas().entrySet()) {
            String town = entry.getKey();
            String expectedFileShuffled = new String(readAllBytes(get(getClass().getResource("../" + town + "-shuffled.txt").toURI())));
            String asmFileShuffled = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-output/data/mapObjects/" + town + ".asm")));
            assertThat(asmFileShuffled).isEqualTo(expectedFileShuffled);
        }
    }

    @Test
    public void shouldShuffleTownArenas() {
        Map<String, String> arenas = arenaShuffler.shuffle(3297392);

        assertThat(arenas.get("ViridianCity")).isEqualTo("CINNABAR_GYM");
        assertThat(arenas.get("VermilionCity")).isEqualTo("SAFFRON_GYM");
        assertThat(arenas.get("CeruleanCity")).isEqualTo("CERULEAN_GYM");
        assertThat(arenas.get("PewterCity")).isEqualTo("VIRIDIAN_GYM");
        assertThat(arenas.get("CeladonCity")).isEqualTo("VERMILION_GYM");
        assertThat(arenas.get("FuchsiaCity")).isEqualTo("CELADON_GYM");
        assertThat(arenas.get("SaffronCity")).isEqualTo("FUCHSIA_GYM");
        assertThat(arenas.get("CinnabarIsland")).isEqualTo("PEWTER_GYM");
    }

    static class FakeAsmFileManager implements IFileManager {

        @Override
        public void write(String filePath, String asmSourceCode) throws IOException {
            FileWriter fileWriter = new FileWriter("/home/likeageek/Projects/randomizer-output/data/mapObjects/" + filePath + ".asm");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(asmSourceCode);
            printWriter.close();
        }

        @Override
        public String read(String filePath) throws IOException {
            return new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/" + filePath + ".asm")));
        }
    }
}

