package com.likeageek.randomizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;

public class App {
    protected int seed;
    protected Map<String, String> arenas = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public void convertAsmFile(String town) throws IOException {
        String arena = arenas.get(town);

        String viridianCityAsm = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/mapObjects/" + town + ".asm")));
        ;
        String[] lines = viridianCityAsm.split("\n\t");

        if (town.equals("ViridianCity")) {
            String viridianCity = replace(lines[7], arena);
            lines[7] = viridianCity + "\n";
        }
        if (town.equals("CeladonCity")) {
            String viridianCity = replace(lines[9], arena);
            lines[9] = viridianCity;
        } else if (town.equals("VermilionCity")) {
            String cinnabarIsland = replace(lines[6], arena);
            lines[6] = cinnabarIsland;
        } else if (town.equals("CeruleanCity")) {
            String cinnabarIsland = replace(lines[6], arena);
            lines[6] = cinnabarIsland;
        } else if (town.equals("PewterCity")) {
            String cinnabarIsland = replace(lines[5], arena);
            lines[5] = cinnabarIsland;
        } else if (town.equals("FuchsiaCity")) {
            String cinnabarIsland = replace(lines[8], arena);
            lines[8] = cinnabarIsland;
        } else if (town.equals("SaffronCity")) {
            String cinnabarIsland = replace(lines[5], arena);
            lines[5] = cinnabarIsland;
        } else if (town.equals("CinnabarIsland")) {
            String cinnabarIsland = replace(lines[4], arena);
            lines[4] = cinnabarIsland;
        }

        String content = String.join("\n\t", lines);
        FileWriter fileWriter = new FileWriter("/home/likeageek/IdeaProjects/" + town + "-shuffled.asm");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(content);
        printWriter.close();
    }

    public void shuffleArenas() {
        List<Town> towns = this.initTowns();

        List<String> shuffledArenas = new ArrayList<>();
        towns.forEach(town -> {
            shuffledArenas.add(town.getArena());
        });
        shuffle(shuffledArenas, new Random(seed));

        for (int i = 0; i < towns.size(); i++) {
            this.arenas.put(towns.get(i).getName(), shuffledArenas.get(i));
        }
    }

    public List<Town> initTowns() {
        return asList(
                Town.builder().name("ViridianCity").arena("VIRIDIAN_GYM").build(),
                Town.builder().name("VermilionCity").arena("VERMILION_GYM").build(),
                Town.builder().name("CeruleanCity").arena("CERULEAN_GYM").build(),
                Town.builder().name("PewterCity").arena("PEWTER_GYM").build(),
                Town.builder().name("CeladonCity").arena("CELADON_GYM").build(),
                Town.builder().name("FuchsiaCity").arena("FUCHSIA_GYM").build(),
                Town.builder().name("SaffronCity").arena("SAFFRON_GYM").build(),
                Town.builder().name("CinnabarIsland").arena("CINNABAR_GYM").build());
    }

    public Map<String, String> getArenas() {
        return arenas;
    }

    private String replace(String line, String arena) {
        String[] viridianGymLineElement = line.split(",");
        viridianGymLineElement[3] = " " + arena;
        return String.join(",", viridianGymLineElement);
    }
}
