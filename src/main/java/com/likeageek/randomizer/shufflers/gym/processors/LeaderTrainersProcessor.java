package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.IRandomEngine;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.entities.Gyms;
import com.likeageek.randomizer.shufflers.gym.entities.Leaders;
import org.apache.commons.lang.WordUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.String.join;
import static java.util.Arrays.asList;

public class LeaderTrainersProcessor {
    private static final String DATA_FILEPATH = "/data/";
    private static final String MAP_OBJECTS_FILEPATH = "mapObjects/";
    private static final String TRAINER_PREFIX = "OPP_";

    private IFileManager asmFileManager;
    private IFileParser asmFileParser;
    private IRandomEngine randomEngine;

    public LeaderTrainersProcessor(IFileManager asmFileManager, IFileParser asmFileParser, IRandomEngine randomEngine) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
        this.randomEngine = randomEngine;
    }

    public void process(Map<String, Object> gyms) {
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
