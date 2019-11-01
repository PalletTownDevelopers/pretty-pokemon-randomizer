package com.likeageek.randomizer.shufflers.gym;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.RandomEngine;
import com.likeageek.randomizer.shufflers.IShuffler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.Cities.*;
import static com.likeageek.randomizer.shufflers.gym.CityBuilder.city;
import static com.likeageek.randomizer.shufflers.gym.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.Gyms.*;
import static java.lang.String.join;
import static java.util.Arrays.asList;

public class GymShuffler implements IShuffler {
    private static final String GYMS_FILE_PATH = "/data/mapObjects/";
    private Map<String, String> gymsShuffled = new HashMap<>();
    private IFileManager asmFileManager;
    private RandomEngine randomEngine = new RandomEngine();

    public GymShuffler(IFileManager asmFileManager) {
        this.asmFileManager = asmFileManager;
        System.out.println("gym shuffler");
    }

    @Override
    public void process(Map<String, String> shuffledEntries) {
        for (Map.Entry<String, String> entry : shuffledEntries.entrySet()) {
            String townName = entry.getKey();
            String arenaToReplace = shuffledEntries.get(townName);
            String[] asmLinesArray;
            try {
                asmLinesArray = readAsmGymFile(townName);
                String asmShuffledFileContent = replaceGymsIntoCityAsm(townName, arenaToReplace, asmLinesArray);
                asmFileManager.write(GYMS_FILE_PATH + townName, asmShuffledFileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, String> shuffle(long seed) {
        List<City> cities = this.buildCities();
        List<String> randomArenas = getRandomGyms(cities, seed);
        for (int townIndex = 0; townIndex < cities.size(); townIndex++) {
            String townName = cities.get(townIndex).getName().toString();
            String townNameReplaced = randomArenas.get(townIndex);
            this.gymsShuffled.put(townName, townNameReplaced);
        }
        return this.gymsShuffled;
    }

    @Override
    public Map<String, String> getResult() {
        return this.gymsShuffled;
    }

    private List<String> getRandomGyms(List<City> cities, long seed) {
        List<String> arenas = new ArrayList<>();
        cities.forEach(city -> arenas.add(city.getGym().getName().toString()));
        return randomEngine.random(arenas, seed);
    }

    private List<City> buildCities() {
        return asList(
                city().name(ViridianCity).arena(gym().name(VIRIDIAN_GYM).build()).build(),
                city().name(VermilionCity).arena(gym().name(VERMILION_GYM).build()).build(),
                city().name(CeruleanCity).arena(gym().name(CERULEAN_GYM).build()).build(),
                city().name(PewterCity).arena(gym().name(PEWTER_GYM).build()).build(),
                city().name(CeladonCity).arena(gym().name(CELADON_GYM).build()).build(),
                city().name(FuchsiaCity).arena(gym().name(FUCHSIA_GYM).build()).build(),
                city().name(SaffronCity).arena(gym().name(SAFFRON_GYM).build()).build(),
                city().name(CinnabarIsland).arena(gym().name(CINNABAR_GYM).build()).build());
    }

    private String[] readAsmGymFile(String townName) throws IOException {
        return asmFileManager.read(GYMS_FILE_PATH + townName).split("\n\t");
    }

    private String replaceGymsIntoCityAsm(String cityName, String gymName, String[] lines) {
        switch (cityName) {
            case "ViridianCity": {
                String viridianCity = replace(lines[7], gymName);
                lines[7] = viridianCity + "\n";
                break;
            }
            case "CeladonCity": {
                String CeladonCity = replace(lines[9], gymName);
                lines[9] = CeladonCity;
                break;
            }
            case "VermilionCity": {
                String VermilionCity = replace(lines[6], gymName);
                lines[6] = VermilionCity;
                break;
            }
            case "CeruleanCity": {
                String CeruleanCity = replace(lines[6], gymName);
                lines[6] = CeruleanCity;
                break;
            }
            case "PewterCity": {
                String PewterCity = replace(lines[5], gymName);
                lines[5] = PewterCity;
                break;
            }
            case "FuchsiaCity": {
                String FuchsiaCity = replace(lines[8], gymName);
                lines[8] = FuchsiaCity;
                break;
            }
            case "SaffronCity": {
                String SaffronCity = replace(lines[5], gymName);
                lines[5] = SaffronCity;
                break;
            }
            case "CinnabarIsland": {
                String cinnabarIsland = replace(lines[4], gymName);
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
}
