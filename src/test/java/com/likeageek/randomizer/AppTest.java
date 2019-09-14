package com.likeageek.randomizer;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;


public class AppTest {
    App app = new App();

    @BeforeEach
    public void init() {
        app.seed = 3297392;
        app.initTowns();
        app.shuffleArenas();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ViridianCity", "VermilionCity", "CeruleanCity", "PewterCity", "CeladonCity", "FuchsiaCity", "SaffronCity", "CinnabarIsland"})
    public void shouldReplaceViridianCityArenaToCinnabarGym(String town) throws URISyntaxException, IOException {
        app.convertAsmFile(town);
        String asmFileShuffled = new String(readAllBytes(get(getClass().getResource(town + "-shuffled.txt").toURI())));
        String expectedAsmFile = new String(readAllBytes(get("/home/likeageek/IdeaProjects/" + town + "-shuffled.asm")));
        assertThat(expectedAsmFile).isEqualTo(asmFileShuffled);
    }

    @Test
    public void shouldInitTowns() {
        assertThat(app.initTowns()).hasSize(8);
    }

    @Test
    public void shouldShuffleTownArenas() {
        app.seed = 3297392;
        app.shuffleArenas();
        Map<String, String> arenas = app.getArenas();
        assertThat(arenas.get("ViridianCity")).isEqualTo("CINNABAR_GYM");
        assertThat(arenas.get("VermilionCity")).isEqualTo("SAFFRON_GYM");
        assertThat(arenas.get("CeruleanCity")).isEqualTo("CERULEAN_GYM");
        assertThat(arenas.get("PewterCity")).isEqualTo("VIRIDIAN_GYM");
        assertThat(arenas.get("CeladonCity")).isEqualTo("VERMILION_GYM");
        assertThat(arenas.get("FuchsiaCity")).isEqualTo("CELADON_GYM");
        assertThat(arenas.get("SaffronCity")).isEqualTo("FUCHSIA_GYM");
        assertThat(arenas.get("CinnabarIsland")).isEqualTo("PEWTER_GYM");
    }
}
