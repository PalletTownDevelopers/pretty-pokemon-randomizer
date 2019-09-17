package com.likeageek.randomizer;

import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.likeageek.randomizer.Towns.*;
import static java.lang.String.join;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;

@Data
public class App {
    private static final String SAVE_FILE_PATH = "/home/likeageek/IdeaProjects/";
    private static final String SAVE_FILE_SUFFIX = "-shuffled.asm";
    private static final String POKEMON_SOURCE_TOWNS_PATH = "/home/likeageek/Projects/randomizer-cache/mapObjects/";
    protected int seed;
    protected Map<String, String> shuffledArenas = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("gameshaker for pokemonredblue");
    }

    public void convertAsmFile(String townName) throws IOException {
        String arenaToReplace = shuffledArenas.get(townName);
        String[] asmLinesArray = readAsmTownFile(townName).split("\n\t");
        String asmShuffledFileContent = replaceArenasByTownName(townName, arenaToReplace, asmLinesArray);
        writeAsmFile(townName, asmShuffledFileContent);
    }

    public void shuffleArenas() {
        List<Town> towns = this.initTowns();

        List<String> shuffledArenas = new ArrayList<>();
        towns.forEach(town -> {
            shuffledArenas.add(town.getArena());
        });
        shuffle(shuffledArenas, new Random(seed));

        for (int i = 0; i < towns.size(); i++) {
            this.shuffledArenas.put(towns.get(i).getName().toString(), shuffledArenas.get(i));
        }
    }

    public List<Town> initTowns() {
        return asList(
                Town.builder().name(ViridianCity).arena("VIRIDIAN_GYM").build(),
                Town.builder().name(VermilionCity).arena("VERMILION_GYM").build(),
                Town.builder().name(CeruleanCity).arena("CERULEAN_GYM").build(),
                Town.builder().name(PewterCity).arena("PEWTER_GYM").build(),
                Town.builder().name(CeladonCity).arena("CELADON_GYM").build(),
                Town.builder().name(FuchsiaCity).arena("FUCHSIA_GYM").build(),
                Town.builder().name(SaffronCity).arena("SAFFRON_GYM").build(),
                Town.builder().name(CinnabarIsland).arena("CINNABAR_GYM").build());
    }

    private String readAsmTownFile(String townName) throws IOException {
        return new String(readAllBytes(get(POKEMON_SOURCE_TOWNS_PATH + townName + ".asm")));
    }

    private String replaceArenasByTownName(String townName, String arena, String[] lines) {
        switch (townName) {
            case "ViridianCity": {
                String viridianCity = replace(lines[7], arena);
                lines[7] = viridianCity + "\n";
                break;
            }
            case "CeladonCity": {
                String CeladonCity = replace(lines[9], arena);
                lines[9] = CeladonCity;
                break;
            }
            case "VermilionCity": {
                String VermilionCity = replace(lines[6], arena);
                lines[6] = VermilionCity;
                break;
            }
            case "CeruleanCity": {
                String CeruleanCity = replace(lines[6], arena);
                lines[6] = CeruleanCity;
                break;
            }
            case "PewterCity": {
                String PewterCity = replace(lines[5], arena);
                lines[5] = PewterCity;
                break;
            }
            case "FuchsiaCity": {
                String FuchsiaCity = replace(lines[8], arena);
                lines[8] = FuchsiaCity;
                break;
            }
            case "SaffronCity": {
                String SaffronCity = replace(lines[5], arena);
                lines[5] = SaffronCity;
                break;
            }
            case "CinnabarIsland": {
                String cinnabarIsland = replace(lines[4], arena);
                lines[4] = cinnabarIsland;
                break;
            }
        }
        return join("\n\t", lines);
    }

    private void writeAsmFile(String townName, String asmSourceCode) throws IOException {
        FileWriter fileWriter = new FileWriter(SAVE_FILE_PATH + townName + SAVE_FILE_SUFFIX);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(asmSourceCode);
        printWriter.close();
    }

    private String replace(String line, String arena) {
        String[] gymLineElements = line.split(",");
        gymLineElements[3] = " " + arena;
        return join(",", gymLineElements);
    }
}
