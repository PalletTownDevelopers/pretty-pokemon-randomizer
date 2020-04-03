package com.likeageek.randomizer.shufflers.gym;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.IRandomEngine;
import com.likeageek.randomizer.shufflers.IShuffler;
import org.apache.commons.lang.WordUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.likeageek.randomizer.shufflers.gym.Cities.*;
import static com.likeageek.randomizer.shufflers.gym.CityBuilder.city;
import static com.likeageek.randomizer.shufflers.gym.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.Gyms.*;
import static com.likeageek.randomizer.shufflers.gym.Leaders.*;
import static java.lang.String.join;
import static java.util.Arrays.asList;

public class GymShuffler implements IShuffler {
    private static final String MAP_OBJECTS_FILEPATH = "mapObjects/";
    private static final String DATA_FILEPATH = "/data/";
    private static final String TRAINER_PREFIX = "OPP_";
    private IFileManager asmFileManager;
    private IFileParser asmFileParser;
    private IRandomEngine randomEngine;
    private Map<String, Integer> cityLineNumber = new HashMap<>() {{
        put("ViridianCity", 7);
        put("CeladonCity", 9);
        put("VermilionCity", 6);
        put("CeruleanCity", 6);
        put("PewterCity", 5);
        put("SaffronCity", 5);
        put("FuchsiaCity", 8);
        put("CinnabarIsland", 4);
    }};

    private Map<String, Integer> gymSetEventLineNumber = new HashMap<>() {{
        put("ViridianGym", 97);
        put("CeladonGym", 34);
        put("SaffronGym", 34);
        put("VermilionGym", 49);
        put("CeruleanGym", 34);
        put("PewterGym", 34);
        put("FuchsiaGym", 36);
        put("CinnabarGym", 115);
    }};

    private Map<String, String> gymLeaderEvents = new HashMap<>() {{
        put("Brock", "EVENT_BEAT_BROCK");
        put("Misty", "EVENT_BEAT_MISTY");
        put("LtSurge", "EVENT_BEAT_LT_SURGE");
        put("Erika", "EVENT_BEAT_ERIKA");
        put("Koga", "EVENT_BEAT_KOGA");
        put("Sabrina", "EVENT_BEAT_SABRINA");
        put("Blaine", "EVENT_BEAT_BLAINE");
        put("Giovanni", "EVENT_BEAT_VIRIDIAN_GYM_GIOVANNI");
    }};

    public GymShuffler(IFileManager asmFileManager, IFileParser asmFileParser, IRandomEngine randomEngine) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
        this.randomEngine = randomEngine;
        System.out.println("gym shuffler");
    }

    @Override
    public Map<String, Object> shuffle() {
        Map<String, Object> gymsRandomized = new HashMap<>();
        List<City> cities = this.buildCities();
        List<Object> citiesRandomized = this.randomEngine.random(new ArrayList<>(cities));

        for (int index = 0; index < cities.size(); index++) {
            City city = cities.get(index);
            String cityName = city.getName().toString();

            Gym gym = ((City) citiesRandomized.get(index)).getGym();
            Gym newGym = gym()
                    .name(Gyms.valueOf(gym.getName().toString()))
                    .warpId(city.getGym().getWarpId())
                    .leaderOld(city.getGym().getLeader())
                    .leader(gym.getLeader())
                    .pokemonRangeLevel(city.getGym().getPokemonRangeLevel())
                    .build();
            gymsRandomized.put(cityName, newGym);
        }
        return gymsRandomized;
    }

    @Override
    public void process(Map<String, Object> gyms) {
        processCitiesWithGyms(gyms);
        processGymsTrainers(gyms);
    }

    private void processCitiesWithGyms(Map<String, Object> gyms) {
        for (Map.Entry<String, Object> entry : gyms.entrySet()) {
            String city = entry.getKey();
            Gym gym = (Gym) (gyms.get(city));
            String gymName = gym.getName().toString();

            buildCityAsmFile(city, gymName);
            buildGymAsmFile(gymName, gym.getWarpId());

            try {
                String gymCamelCase = WordUtils.capitalize(WordUtils.capitalizeFully(gymName, new char[]{'_'}).replaceAll("_", ""));
                String[] lines = this.asmFileManager.read("/scripts/" + gymCamelCase);
                int index = gymSetEventLineNumber.get(gymCamelCase);
                lines[index] = "SetEvent " + gymLeaderEvents.get(gym.getLeaderOld().name());
                this.asmFileManager.write("/scripts/" + gymCamelCase, join("\n\t", lines));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processGymsTrainers(Map<String, Object> gyms) {
        String filePath = DATA_FILEPATH + "trainer_parties";
        try {
            String[] lines = this.asmFileManager.read(filePath);
            for (Map.Entry<String, Object> entry : gyms.entrySet()) {
                String city = entry.getKey();
                Gym gym = (Gym) (gyms.get(city));
                getTrainers(gym.getName()).forEach((trainerName, trainerOppPosition) -> {
                    buildAsmTrainerPartiesForTrainers(trainerName, trainerOppPosition, gym.getPokemonRangeLevel(), lines);
                });
                buildAsmTrainerPartiesForLeaders(gym.getLeader(), gym.getPokemonRangeLevel(), lines);
            }
            String join = join("\n\t", lines);
            this.asmFileManager.write(filePath, join);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildCityAsmFile(String city, String gymToReplace) {
        try {
            String[] lines = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + city);
            String cityFileContent = buildAsmFileCity(city, gymToReplace, lines);
            this.asmFileManager.write(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + city, cityFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildAsmTrainerPartiesForLeaders(Leaders leader, Integer[] pokemonRangeLevel, String[] lines) {
        List<String> linesList = asList(lines);
        OptionalInt gymLeaderDataIndex = IntStream.range(0, linesList.size())
                .filter(i -> linesList.get(i).contains(leader.name() + "Data:"))
                .findFirst();

        int oppFromGymFile = 1;
        if ("Giovanni".equals(leader.name())) {
            oppFromGymFile = 3;
        }
        int GymLeaderIndex = gymLeaderDataIndex.getAsInt() + oppFromGymFile;

        String[] pokemonGymLeader = lines[GymLeaderIndex].split(",");
        for (int position = 2; position < pokemonGymLeader.length; position += 2) {
            Object pokemonLevel = randomEngine.randomBetweenRangeValues(asList(pokemonRangeLevel));
            lines[GymLeaderIndex] = this.asmFileParser.editLine(lines[GymLeaderIndex], String.valueOf(pokemonLevel), position);
        }
    }

    private void buildAsmTrainerPartiesForTrainers(String trainerName, List<Integer> trainerOppPosition, Integer[] pokemonRangeLevel, String[] lines) {
        List<String> linesList = asList(lines);
        OptionalInt gymLeaderDataIndex = IntStream.range(0, linesList.size())
                .filter(i -> linesList.get(i).contains(trainerName + "Data:"))
                .findFirst();

        for (int oppFromGymFile : trainerOppPosition) {
            int GymLeaderIndex = gymLeaderDataIndex.getAsInt() + oppFromGymFile;

            int position = 1;
            Object pokemonLevel = randomEngine.randomBetweenRangeValues(asList(pokemonRangeLevel));
            lines[GymLeaderIndex] = this.asmFileParser.editLine(lines[GymLeaderIndex], String.valueOf(pokemonLevel), position);
        }
    }

    private void buildGymAsmFile(String gym, int warpId) {
        try {
            String gymName = Gyms.valueOf(gym).getName();
            String[] gymLines = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymName);
            String gymFileContent = buildAsmFileGym(warpId, gymLines);
            this.asmFileManager.write(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymName, gymFileContent);
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
        Gym pewterGym = gym().warpId(2).name(PEWTER_GYM).leader(Brock).trainers(getTrainers(PEWTER_GYM)).pokemonRangeLevel(new Integer[]{12, 14}).build();
        Gym ceruleanGym = gym().warpId(3).name(CERULEAN_GYM).leader(Misty).trainers(getTrainers(CERULEAN_GYM)).pokemonRangeLevel(new Integer[]{18, 21}).build();
        Gym vermilionGym = gym().warpId(3).name(VERMILION_GYM).leader(LtSurge).trainers(getTrainers(VERMILION_GYM)).pokemonRangeLevel(new Integer[]{18, 24}).build();
        Gym celadonGym = gym().warpId(6).name(CELADON_GYM).leader(Erika).trainers(getTrainers(CELADON_GYM)).pokemonRangeLevel(new Integer[]{24, 29}).build();
        Gym fuchsiaGym = gym().warpId(5).name(FUCHSIA_GYM).leader(Koga).trainers(getTrainers(FUCHSIA_GYM)).pokemonRangeLevel(new Integer[]{37, 43}).build();
        Gym saffronGym = gym().warpId(2).name(SAFFRON_GYM).leader(Sabrina).trainers(getTrainers(SAFFRON_GYM)).pokemonRangeLevel(new Integer[]{37, 43}).build();
        Gym cinnarbarGym = gym().warpId(1).name(CINNABAR_GYM).leader(Blaine).trainers(getTrainers(CINNABAR_GYM)).pokemonRangeLevel(new Integer[]{40, 47}).build();
        Gym viridianGym = gym().warpId(4).name(VIRIDIAN_GYM).leader(Giovanni).trainers(getTrainers(VIRIDIAN_GYM)).pokemonRangeLevel(new Integer[]{42, 50}).build();

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

    public Map<String, List<Integer>> getTrainers(Gyms gymFileName) {
        String[] gymFile = new String[0];
        try {
            gymFile = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymFileName.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<Integer>> trainersOpp = new HashMap<>();
        List<String> gymLines = asList(gymFile);
        Object[] trainers = gymLines.stream().filter(s -> s.contains(TRAINER_PREFIX)).toArray();
        trainers[0] = null;

        for (int i = 1; i < trainers.length; i++) {
            String trainerLine = (String) trainers[i];
            String[] line = trainerLine.split(",");
            String name = line[line.length - 2].replace(TRAINER_PREFIX, "").trim();
            String nameCamelCase = WordUtils.capitalize(WordUtils.capitalizeFully(name, new char[]{'_'}).replaceAll("_", ""));

            Integer opp = Integer.valueOf(line[line.length - 1].trim());
            if (trainersOpp.containsKey(nameCamelCase)) {
                trainersOpp.get(nameCamelCase).add(opp);
            } else {
                List<Integer> integers = new ArrayList<>();
                integers.add(opp);
                if (nameCamelCase.equals("PsychicTr")) {
                    nameCamelCase = "Psychic";
                }
                trainersOpp.put(nameCamelCase, integers);
            }

        }
        return trainersOpp;
    }
}
