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
    private static final String MAP_OBJECTS_FILEPATH = "/data/mapObjects/";
    private Map<String, Object> gymsRandomized = new HashMap<>();
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
    public void process(Map<String, Object> gyms) {
        for (Map.Entry<String, Object> entry : gyms.entrySet()) {
            String city = entry.getKey();
            String gymToReplace = ((Gym) (gyms.get(city))).getName().toString();
            int warpId = ((Gym) (gyms.get(city))).getWarpId();
            buildCityAsmFile(city, gymToReplace);
            buildGymAsmFile(gymToReplace, warpId);
        }
    }

    private void buildCityAsmFile(String city, String gymToReplace) {
        try {
            String[] lines = readAsmFile(city);
            String fileCityShuffled = buildAsmFileCity(city, gymToReplace, lines);
            String filePath = MAP_OBJECTS_FILEPATH + city;
            asmFileManager.write(filePath, fileCityShuffled);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGymAsmFile(String gym, int warpId) {
        try {
            String gymName = Gyms.valueOf(gym).getName();
            String[] lines = readAsmFile(gymName);
            String fileGymShuffled = buildAsmFileGym(gymName, warpId, lines);
            String filePath = MAP_OBJECTS_FILEPATH + gymName;
            asmFileManager.write(filePath, fileGymShuffled);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildAsmFileGym(String gym, int warpId, String[] lines) {
        lines[3] = replaceWarpId(lines[3], warpId);
        lines[4] = replaceWarpId(lines[4], warpId);
        return join("\n\t", lines);
    }

    @Override
    public Map<String, Object> shuffle(long seed) {
        List<String> citiesRandomized = getCitiesRandomized(this.cities, seed);
        for (int index = 0; index < this.cities.size(); index++) {
            String cityName = this.cities.get(index).getName().toString();
            int cityWarpId = this.cities.get(index).getGym().getWarpId();

            String gymName = citiesRandomized.get(index);
            Gym gym = gym().name(Gyms.valueOf(gymName)).warpId(cityWarpId).build();
            this.gymsRandomized.put(cityName, gym);
        }
        return this.gymsRandomized;
    }

    @Override
    public Map<String, Object> getResult() {
        return this.gymsRandomized;
    }

    private List<String> getCitiesRandomized(List<City> cities, long seed) {
        List<String> citiesRandomized = new ArrayList<>();
        cities.forEach(city -> citiesRandomized.add(city.getGym().getName().toString()));
        return randomEngine.random(citiesRandomized, seed);
    }

    private List<City> buildCities() {
        return asList(
                city().name(ViridianCity).arena(gym().warpId(4).name(VIRIDIAN_GYM).build()).build(),
                city().name(VermilionCity).arena(gym().warpId(3).name(VERMILION_GYM).build()).build(),
                city().name(CeruleanCity).arena(gym().warpId(3).name(CERULEAN_GYM).build()).build(),
                city().name(PewterCity).arena(gym().warpId(2).name(PEWTER_GYM).build()).build(),
                city().name(CeladonCity).arena(gym().warpId(6).name(CELADON_GYM).build()).build(),
                city().name(FuchsiaCity).arena(gym().warpId(5).name(FUCHSIA_GYM).build()).build(),
                city().name(SaffronCity).arena(gym().warpId(2).name(SAFFRON_GYM).build()).build(),
                city().name(CinnabarIsland).arena(gym().warpId(1).name(CINNABAR_GYM).build()).build());
    }

    private String[] readAsmFile(String fileName) throws IOException {
        return asmFileManager.read(MAP_OBJECTS_FILEPATH + fileName).split("\n\t");
    }

    private String buildAsmFileCity(String cityName, String gymName, String[] lines) {
        switch (cityName) {
            case "ViridianCity": {
                String viridianCity = replaceGymName(lines[7], gymName);
                lines[7] = viridianCity + "\n";
                break;
            }
            case "CeladonCity": {
                String CeladonCity = replaceGymName(lines[9], gymName);
                lines[9] = CeladonCity;
                break;
            }
            case "VermilionCity": {
                String VermilionCity = replaceGymName(lines[6], gymName);
                lines[6] = VermilionCity;
                break;
            }
            case "CeruleanCity": {
                String CeruleanCity = replaceGymName(lines[6], gymName);
                lines[6] = CeruleanCity;
                break;
            }
            case "PewterCity": {
                String PewterCity = replaceGymName(lines[5], gymName);
                lines[5] = PewterCity;
                break;
            }
            case "FuchsiaCity": {
                String FuchsiaCity = replaceGymName(lines[8], gymName);
                lines[8] = FuchsiaCity;
                break;
            }
            case "SaffronCity": {
                String SaffronCity = replaceGymName(lines[5], gymName);
                lines[5] = SaffronCity;
                break;
            }
            case "CinnabarIsland": {
                String cinnabarIsland = replaceGymName(lines[4], gymName);
                lines[4] = cinnabarIsland;
                break;
            }
        }
        return join("\n\t", lines);
    }

    private String replaceGymName(String line, String arena) {
        String[] gymLineElements = line.split(",");
        gymLineElements[3] = " " + arena;
        return join(",", gymLineElements);
    }

    private String replaceWarpId(String line, int warpId) {
        String[] gymLineElements = line.split(",");
        gymLineElements[2] = " ".concat(Integer.toString(warpId));
        return join(",", gymLineElements);
    }
}
