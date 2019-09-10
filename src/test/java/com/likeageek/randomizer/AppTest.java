package com.likeageek.randomizer;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class AppTest {
    App app = new App();

    @Test
    public void shouldReplaceViridianCityArenaToCinnabarGym() throws URISyntaxException, IOException {
        HashMap<String, String> arenas = (HashMap<String, String>) app.getArenas();
        arenas.put("ViridianCity", "CINNABAR_GYM");

        String outputFileContent = app.convertAsmFile(arenas);
        String viridianCityExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource("ViridianCity-shuffled.txt").toURI())));
        assertThat(outputFileContent).isEqualTo(viridianCityExpected);
    }

    @Test
    public void shouldReplaceVermilionCityArenaToSaffronGym() throws URISyntaxException, IOException {
        HashMap<String, String> arenas = (HashMap<String, String>) app.getArenas();
        arenas.put("VermilionCity", "SAFFRON_GYM");

        String outputFileContent = app.convertAsmFile(arenas);
        String viridianCityExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource("VermilionCity-shuffled.txt").toURI())));
        assertThat(outputFileContent).isEqualTo(viridianCityExpected);
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
