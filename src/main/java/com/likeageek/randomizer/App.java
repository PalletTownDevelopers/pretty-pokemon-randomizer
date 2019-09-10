package com.likeageek.randomizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;

public class App {
    protected int seed;
    protected Map<String, String> arenas = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public String convertAsmFile(HashMap<String, String> arenas) throws URISyntaxException, IOException {
        String town = arenas.keySet().toArray()[0].toString();
        String arena = arenas.get(arenas.keySet().toArray()[0]);

        String viridianCityAsm = new String(Files.readAllBytes(Paths.get(getClass().getResource(town + ".asm").toURI())));
        String[] lines = viridianCityAsm.split("\n\t");
        if(town.equals("ViridianCity")){
            String viridianGymElement = replace(lines[7], arena);
            lines[7] = viridianGymElement  + "\n";
        }else{
            String vermilionGymElement = replace(lines[6], arena);
            lines[6] = vermilionGymElement;
        }

        return String.join("\n\t", lines);
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
