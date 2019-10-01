package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.Town;

import java.io.IOException;
import java.util.*;

import static com.likeageek.randomizer.TownBuilder.town;
import static com.likeageek.randomizer.Towns.*;
import static java.lang.String.join;
import static java.util.Arrays.asList;

public class ArenaShuffler implements IShuffler {
    private Map<String, String> shuffledArenas = new HashMap<>();
    private IFileManager asmFileManager;
    private static final String TOWNS = "mapObjects/";

    public ArenaShuffler(IFileManager asmFileManager) {
        this.asmFileManager = asmFileManager;
    }

    @Override
    public void process(Map<String, String> shuffledEntries) throws IOException {
        for (Map.Entry<String, String> entry : shuffledEntries.entrySet()) {
            String townName = entry.getKey();
            String arenaToReplace = shuffledEntries.get(townName);
            String[] asmLinesArray = readAsmTownFile(townName);
            String asmShuffledFileContent = replaceArenasByTownName(townName, arenaToReplace, asmLinesArray);
            asmFileManager.write(townName, asmShuffledFileContent);
        }
    }

    @Override
    public Map<String, String> shuffle(int seed) {
        List<Town> towns = this.buildTowns();
        List<String> randomArenas = getRandomArenas(towns, seed);
        for (int i = 0; i < towns.size(); i++) {
            this.shuffledArenas.put(towns.get(i).getName().toString(), randomArenas.get(i));
        }
        return this.shuffledArenas;
    }

    private List<String> getRandomArenas(List<Town> towns, int seed) {
        List<String> arenas = new ArrayList<>();
        towns.forEach(town -> arenas.add(town.getArena()));
        Collections.shuffle(arenas, new Random(seed));
        return arenas;
    }

    private List<Town> buildTowns() {
        return asList(
                town().name(ViridianCity).arena("VIRIDIAN_GYM").build(),
                town().name(VermilionCity).arena("VERMILION_GYM").build(),
                town().name(CeruleanCity).arena("CERULEAN_GYM").build(),
                town().name(PewterCity).arena("PEWTER_GYM").build(),
                town().name(CeladonCity).arena("CELADON_GYM").build(),
                town().name(FuchsiaCity).arena("FUCHSIA_GYM").build(),
                town().name(SaffronCity).arena("SAFFRON_GYM").build(),
                town().name(CinnabarIsland).arena("CINNABAR_GYM").build());
    }

    private String[] readAsmTownFile(String townName) throws IOException {
        return asmFileManager.read(TOWNS + townName).split("\n\t");
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

    private String replace(String line, String arena) {
        String[] gymLineElements = line.split(",");
        gymLineElements[3] = " " + arena;
        return join(",", gymLineElements);
    }

    public Map<String, String> getShuffledArenas() {
        return shuffledArenas;
    }
}
