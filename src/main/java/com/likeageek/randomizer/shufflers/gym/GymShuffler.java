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
    private Map<String, String> gymsRandomized = new HashMap<>();
    private IFileManager asmFileManager;
    private RandomEngine randomEngine;
    private List<City> cities;

    public GymShuffler(IFileManager asmFileManager) {
        this.asmFileManager = asmFileManager;
        cities = this.buildCities();
        randomEngine = new RandomEngine();
        System.out.println("gym shuffler");
    }

    @Override
    public void process(Map<String, String> gyms) {
        for (Map.Entry<String, String> entry : gyms.entrySet()) {
            String city = entry.getKey();
            String gymToReplace = gyms.get(city);
            buildAsmFile(city, gymToReplace);
        }
    }

    private void buildAsmFile(String city, String gymToReplace) {
        try {
            String[] lines = readAsmGymFile(city);
            String fileCityShuffled = buildAsmFileCity(city, gymToReplace, lines);
            String filePath = GYMS_FILE_PATH + city;
            asmFileManager.write(filePath, fileCityShuffled);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> shuffle(long seed) {
        List<String> gymsRandomized = getGymsRandomized(this.cities, seed);
        for (int townIndex = 0; townIndex < this.cities.size(); townIndex++) {
            String city = this.cities.get(townIndex).getName().toString();
            String gym = gymsRandomized.get(townIndex);
            this.gymsRandomized.put(city, gym);
        }
        return this.gymsRandomized;
    }

    @Override
    public Map<String, String> getResult() {
        return this.gymsRandomized;
    }

    private List<String> getGymsRandomized(List<City> cities, long seed) {
        List<String> gyms = new ArrayList<>();
        cities.forEach(city -> gyms.add(city.getGym().getName().toString()));
        return randomEngine.random(gyms, seed);
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

    private String buildAsmFileCity(String cityName, String gymName, String[] lines) {
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
