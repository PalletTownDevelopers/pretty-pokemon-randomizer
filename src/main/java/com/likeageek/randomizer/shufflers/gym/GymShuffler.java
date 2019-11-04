package com.likeageek.randomizer.shufflers.gym;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.RandomEngine;
import com.likeageek.randomizer.shufflers.IShuffler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.Cities.*;
import static com.likeageek.randomizer.shufflers.gym.CityBuilder.city;
import static com.likeageek.randomizer.shufflers.gym.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.Gyms.*;
import static com.likeageek.randomizer.shufflers.gym.Trainers.*;
import static java.lang.String.join;
import static java.util.Arrays.asList;

public class GymShuffler implements IShuffler {
    private static final String MAP_OBJECTS_FILEPATH = "/data/mapObjects/";
    private Map<String, Object> gymsRandomized = new HashMap<>();
    private IFileManager asmFileManager;
    private IFileParser asmFileParser;
    private RandomEngine randomEngine;
    private List<City> cities;

    public GymShuffler(IFileManager asmFileManager, IFileParser asmFileParser) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
        this.cities = this.buildCities();
        this.randomEngine = new RandomEngine();
        System.out.println("gym shuffler");
    }

    @Override
    public Map<String, Object> shuffle(long seed) {
        List<City> citiesRandomized = this.randomEngine.random(this.cities, seed);

        for (int index = 0; index < this.cities.size(); index++) {
            City city = this.cities.get(index);
            String cityName = city.getName().toString();
            int cityWarpId = city.getGym().getWarpId();

            Gym gym = citiesRandomized.get(index).getGym();
            String gymName = gym.getName().toString();
            Trainers trainer = gym.getTrainer();
            int[] pokemonLevelRange = gym.getPokemonRangeLevel();

            Gym newGym = gym()
                    .name(Gyms.valueOf(gymName))
                    .warpId(cityWarpId)
                    .trainer(trainer)
                    .pokemonRangeLevel(pokemonLevelRange)
                    .build();
            this.gymsRandomized.put(cityName, newGym);
        }
        return this.gymsRandomized;
    }

    @Override
    public void process(Map<String, Object> gyms) {
        for (Map.Entry<String, Object> entry : gyms.entrySet()) {
            String city = entry.getKey();
            Gym gym = (Gym) (gyms.get(city));
            String gymName = gym.getName().toString();
            int warpId = gym.getWarpId();
            buildCityAsmFile(city, gymName);
            buildGymAsmFile(gymName, warpId);
        }
    }

    @Override
    public Map<String, Object> getResult() {
        return this.gymsRandomized;
    }

    private void buildCityAsmFile(String city, String gymToReplace) {
        try {
            String[] lines = readAsmFile(city);
            String cityFileContent = buildAsmFileCity(city, gymToReplace, lines);
            this.asmFileManager.write(MAP_OBJECTS_FILEPATH + city, cityFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGymAsmFile(String gym, int warpId) {
        try {
            String gymName = Gyms.valueOf(gym).getName();
            String[] lines = readAsmFile(gymName);
            String gymFileContent = buildAsmFileGym(warpId, lines);
            this.asmFileManager.write(MAP_OBJECTS_FILEPATH + gymName, gymFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildAsmFileGym(int warpId, String[] lines) {
        lines[3] = this.asmFileParser.editLine(lines[3], Integer.toString(warpId), 3);
        lines[4] = this.asmFileParser.editLine(lines[4], Integer.toString(warpId), 3);
        return join("\n\t", lines);
    }

    private List<City> buildCities() {
        Gym pewterGym = gym().warpId(2).name(PEWTER_GYM).trainer(Brock).pokemonRangeLevel(new int[]{12, 14}).build();
        Gym ceruleanGym = gym().warpId(3).name(CERULEAN_GYM).trainer(Misty).pokemonRangeLevel(new int[]{18, 21}).build();
        Gym vermilionGym = gym().warpId(3).name(VERMILION_GYM).trainer(Misty).pokemonRangeLevel(new int[]{18, 24}).build();
        Gym celadonGym = gym().warpId(6).name(CELADON_GYM).trainer(Erika).pokemonRangeLevel(new int[]{24, 29}).build();
        Gym fuchsiaGym = gym().warpId(5).name(FUCHSIA_GYM).trainer(Koga).pokemonRangeLevel(new int[]{37, 43}).build();
        Gym saffronGym = gym().warpId(2).name(SAFFRON_GYM).trainer(Sabrina).pokemonRangeLevel(new int[]{37, 43}).build();
        Gym cinnarbarGym = gym().warpId(1).name(CINNABAR_GYM).trainer(Blaine).pokemonRangeLevel(new int[]{40, 47}).build();
        Gym viridianGym = gym().warpId(4).name(VIRIDIAN_GYM).trainer(Giovanni).pokemonRangeLevel(new int[]{42, 50}).build();

        return asList(
                city().name(PewterCity).gym(pewterGym).build(),
                city().name(CeruleanCity).gym(ceruleanGym).build(),
                city().name(VermilionCity).gym(vermilionGym).build(),
                city().name(CeladonCity).gym(celadonGym).build(),
                city().name(FuchsiaCity).gym(fuchsiaGym).build(),
                city().name(SaffronCity).gym(saffronGym).build(),
                city().name(CinnabarIsland).gym(cinnarbarGym).build(),
                city().name(ViridianCity).gym(viridianGym).build());
    }

    private String[] readAsmFile(String fileName) throws IOException {
        return this.asmFileManager.read(MAP_OBJECTS_FILEPATH + fileName).split("\n\t");
    }

    private String buildAsmFileCity(String cityName, String gymName, String[] lines) {
        Integer lineNumber = this.cityLineNumber.get(cityName);
        lines[lineNumber] = this.asmFileParser.editLine(lines[lineNumber], gymName, 4);
        addNewlineToViridianCity(cityName, lines, lineNumber);
        return join("\n\t", lines);
    }

    private void addNewlineToViridianCity(String cityName, String[] lines, Integer lineNumber) {
        if ("ViridianCity".equals(cityName)) {
            lines[lineNumber] = lines[lineNumber].concat("\n");
        }
    }

    private Map<String, Integer> cityLineNumber = new HashMap<String, Integer>() {{
        put("ViridianCity", 7);
        put("CeladonCity", 9);
        put("VermilionCity", 6);
        put("CeruleanCity", 6);
        put("PewterCity", 5);
        put("SaffronCity", 5);
        put("FuchsiaCity", 8);
        put("CinnabarIsland", 4);
    }};
}
