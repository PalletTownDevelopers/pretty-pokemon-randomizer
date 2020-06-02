package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.IRandomEngine;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.entities.Gyms;
import com.likeageek.randomizer.shufflers.gym.entities.Leaders;

import java.util.*;
import java.util.stream.IntStream;

import static com.likeageek.randomizer.shufflers.gym.entities.Leaders.Giovanni;
import static java.util.Arrays.asList;
import static org.apache.commons.lang.WordUtils.capitalize;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

public class LeaderTrainersProcessor {
    private static final String DATA_FILEPATH = "/data/";
    private static final String ENGINE_FILEPATH = "/engine/";
    private static final String MAP_OBJECTS_FILEPATH = "mapObjects/";
    private static final String TRAINER_PREFIX = "OPP_";
    private static final String TRAINER_PARTIES = "trainer_parties";
    private static final String START_SUB_MENU = "menu/start_sub_menus";

    private IFileManager asmFileManager;
    private IFileParser asmFileParser;
    private IRandomEngine randomEngine;

    public LeaderTrainersProcessor(IFileManager asmFileManager,
                                   IFileParser asmFileParser,
                                   IRandomEngine randomEngine) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
        this.randomEngine = randomEngine;
    }

    public void process(Map<String, Object> gyms) {
        String filePath = DATA_FILEPATH + TRAINER_PARTIES;
        String[] lines = this.asmFileManager.read(filePath);
        for (Map.Entry<String, Object> entry : gyms.entrySet()) {
            String city = entry.getKey();
            Gym gym = (Gym) (gyms.get(city));
            getTrainers(gym.getName()).forEach((trainerName, trainerOppPosition) -> {
                buildAsmTrainerPartiesForTrainers(trainerName, trainerOppPosition, gym.getPokemonRangeLevel(), lines);
            });
            buildAsmTrainerPartiesForLeaders(gym.getLeader(), gym.getPokemonRangeLevel(), lines);
        }
        this.asmFileManager.write(filePath, lines);

        disableCheckHM();
    }

    private void buildAsmTrainerPartiesForTrainers(String trainerName,
                                                   List<Integer> trainerOppPosition,
                                                   Integer[] pokemonRangeLevel,
                                                   String[] lines) {
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

    private void disableCheckHM() {
        String filePath = ENGINE_FILEPATH + START_SUB_MENU;
        String[] lines = this.asmFileManager.read(filePath);

        //Disable check for fly HM
        lines[118] = ";" + lines[118];
        lines[119] = ";" + lines[119];

        //Disable check for cut HM
        lines[136] = ";" + lines[136];
        lines[137] = ";" + lines[137];

        //Disable check for surf HM
        lines[143] = ";" + lines[143];
        lines[144] = ";" + lines[144];

        //Disable check for strength HM
        lines[159] = ";" + lines[159];
        lines[160] = ";" + lines[160];

        //Disable check for flash HM
        lines[164] = ";" + lines[164];
        lines[165] = ";" + lines[165];

        this.asmFileManager.write(filePath, lines);
    }

    private void buildAsmTrainerPartiesForLeaders(Leaders leader,
                                                  Integer[] pokemonRangeLevel,
                                                  String[] lines) {
        List<String> linesList = asList(lines);
        OptionalInt gymLeaderDataIndex = IntStream.range(0, linesList.size())
                .filter(i -> linesList.get(i).contains(leader.name() + "Data:"))
                .findFirst();

        int oppFromGymFile = 1;
        if (leader.name().equals(Giovanni.name())) {
            oppFromGymFile = 3;
        }
        int GymLeaderIndex = gymLeaderDataIndex.getAsInt() + oppFromGymFile;

        String[] pokemonGymLeader = lines[GymLeaderIndex].split(",");
        for (int position = 2; position < pokemonGymLeader.length; position += 2) {
            Object pokemonLevel = randomEngine.randomBetweenRangeValues(asList(pokemonRangeLevel));
            lines[GymLeaderIndex] = this.asmFileParser.editLine(lines[GymLeaderIndex], String.valueOf(pokemonLevel), position);
        }
    }

    protected Map<String, List<Integer>> getTrainers(Gyms gymFileName) {
        String[] gymFile = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymFileName.getName());
        Map<String, List<Integer>> trainersOpp = new HashMap<>();
        List<String> gymLines = asList(gymFile);
        Object[] trainers = gymLines.stream().filter(s -> s.contains(TRAINER_PREFIX)).toArray();
        trainers[0] = null;

        for (int i = 1; i < trainers.length; i++) {
            String trainerLine = (String) trainers[i];
            String[] line = trainerLine.split(",");
            String name = line[line.length - 2].replace(TRAINER_PREFIX, "").trim();
            String nameCamelCase = convertToCamelCase(name);

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

    private String convertToCamelCase(String name) {
        return capitalize(capitalizeFully(name, new char[]{'_'}).replaceAll("_", ""));
    }
}
