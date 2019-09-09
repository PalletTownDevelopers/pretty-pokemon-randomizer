package com.likeageek.randomizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;

/**
 * Hello world!
 */
public class App {
    protected int seed;
    protected Map<String, String> shuffleArenas = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public String convertAsmFile() throws URISyntaxException, IOException {
        String viridianCityAsm = new String(Files.readAllBytes(Paths.get(getClass().getResource("ViridianCity.asm").toURI())));

        String[] lines = viridianCityAsm.split("\n\t");
        String viridianGymElement = replace(lines[7]);
        lines[7] = viridianGymElement;

        return String.join("\n\t", lines);
    }

    public void shuffleArenas() {
        List<String> arenas = asList("Viridian", "Vermilion");

        List<String> shuffledArenas = new ArrayList<>();
        shuffledArenas.addAll(arenas);
        shuffle(shuffledArenas, new Random(seed));

        for (int i = 0; i < arenas.size(); i++) {
            shuffleArenas.put(arenas.get(i), shuffledArenas.get(i));
        }
    }

    private String replace(String line) {
        String[] viridianGymLineElement = line.split(",");
        viridianGymLineElement[3] = " VERMILION_GYM" + "\n";
        return String.join(",", viridianGymLineElement);
    }
}
