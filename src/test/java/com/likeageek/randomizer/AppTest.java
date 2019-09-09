package com.likeageek.randomizer;

import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class AppTest 
{
    App app = new App();

    @Test
    public void shouldReplaceViridianGymToVermilionGym() throws URISyntaxException, IOException {
        String outputFileContent = app.convertAsmFile();

        String viridianCityExpected = new String(Files.readAllBytes(Paths.get(getClass().getResource("ViridianCity.txt").toURI())));
        assertEquals(viridianCityExpected, outputFileContent);
    }

    @Test
    public void shouldShuffleArenas(){
        app.seed = 3297392;
        app.shuffleArenas();
        assertEquals("Vermilion", app.shuffleArenas.get("Viridian"));
    }
}
