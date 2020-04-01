package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.FakeAsmFileManager;
import com.likeageek.randomizer.RandomEngine;
import com.likeageek.randomizer.shufflers.gym.Gym;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;
import com.likeageek.randomizer.shufflers.gym.Leaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.Gyms.*;
import static com.likeageek.randomizer.shufflers.gym.Leaders.*;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class GymShufflerTest {
    private GymShuffler gymShuffler;

    @BeforeEach
    public void init() {
        RandomEngine randomEngine = new RandomEngine(3297392);
        randomEngine.setTest(true);
        gymShuffler = new GymShuffler(new FakeAsmFileManager(), new AsmFileParser(), randomEngine);
    }

    @Test
    public void shouldProcessGymsWithWarpID() throws URISyntaxException, IOException {
        Map<String, Object> cities = new HashMap<>();
        Integer[] viridianRangeLevel = {42, 50};
        int viridianWarpId = 4;
        Leaders celadonTrainer = Erika;
        cities.put("ViridianCity", gym().warpId(viridianWarpId).pokemonRangeLevel(viridianRangeLevel)
                .name(CELADON_GYM).leader(celadonTrainer).build());

        Integer[] pewterRangeLevel = {12, 14};
        int pewterWarpId = 2;
        Leaders saffronTrainer = Sabrina;
        cities.put("PewterCity", gym().warpId(pewterWarpId).pokemonRangeLevel(pewterRangeLevel)
                .name(SAFFRON_GYM).leader(saffronTrainer).build());

        Integer[] ceruleanRangeLevel = {24, 29};
        int ceruleanWarpId = 3;
        Leaders viridianTrainer = Giovanni;
        cities.put("CeruleanCity", gym().warpId(ceruleanWarpId).pokemonRangeLevel(ceruleanRangeLevel)
                .name(VIRIDIAN_GYM).leader(viridianTrainer).build());

        gymShuffler.process(cities);

        String outputPath = "/home/likeageek/Projects/randomizer-output/data/";

        for (Map.Entry<String, Object> entry : cities.entrySet()) {
            String cityName = entry.getKey();
            Object gym = entry.getValue();
            String expectedCityAsmFile = new String(readAllBytes(get(getClass().getResource("../mapObjects/" + cityName + "-shuffled.txt").toURI())));
            String cityAsmFile = new String(readAllBytes(get(outputPath + "mapObjects/" + cityName + ".asm")));
            assertThat(cityAsmFile).isEqualTo(expectedCityAsmFile);

            String gymName = ((Gym) gym).getName().getName();
            String expectedGymAsmFile = new String(readAllBytes(get(getClass().getResource("../mapObjects/" + gymName + "-shuffled.txt").toURI())));
            String gymAsmFile = new String(readAllBytes(get(outputPath + "mapObjects/" + gymName + ".asm")));
            assertThat(gymAsmFile).isEqualTo(expectedGymAsmFile);
        }

        String expectedTrainerPartiesAsmFile = new String(readAllBytes(get(getClass().getResource("../mapObjects/trainer_parties-shuffled.txt").toURI())));
        String trainerPartiesAsmFile = new String(readAllBytes(get(outputPath + "/trainer_parties.asm")));
        assertThat(trainerPartiesAsmFile).isEqualTo(expectedTrainerPartiesAsmFile);
    }

    @Test
    public void shouldShuffleGyms() {
        Integer[] viridianRangeLevel = {42, 50};
        int viridianWarpId = 4;
        Leaders celadonTrainer = Erika;

        Map<String, Object> gyms = gymShuffler.shuffle();

        Gym newViridianGym = gym().warpId(viridianWarpId).leader(celadonTrainer).pokemonRangeLevel(viridianRangeLevel).name(CELADON_GYM).build();
        assertThat(gyms.get("ViridianCity")).isEqualToComparingFieldByField(newViridianGym);
    }

    @Test
    public void shouldReadGymTrainersAndTransformNameToCamelCase() {
        Map<String, List<Integer>> trainers = gymShuffler.getTrainers(CELADON_GYM);
        assertThat(trainers.size()).isEqualTo(4);
        assertThat(trainers.get("Beauty")).isEqualTo(asList(1, 2, 3));
        assertThat(trainers.get("Lass")).isEqualTo(asList(17, 18));
        assertThat(trainers.get("CooltrainerF")).isEqualTo(singletonList(1));
        assertThat(trainers.get("JrTrainerF")).isEqualTo(singletonList(11));
    }

    @Test
    public void shouldReadGymTrainersAndReplacePsychicTr_whenSaffronGym() {
        Map<String, List<Integer>> trainers = gymShuffler.getTrainers(SAFFRON_GYM);
        assertThat(trainers.size()).isEqualTo(2);
        assertThat(trainers.get("Channeler")).isEqualTo(asList(22, 23, 24));
        assertThat(trainers.get("Psychic")).isEqualTo(singletonList(4));
    }

}

