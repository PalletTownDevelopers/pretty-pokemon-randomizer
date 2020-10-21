package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.FakeAsmFileManager;
import com.likeageek.randomizer.RandomEngine;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.entities.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.entities.Gyms.*;
import static com.likeageek.randomizer.shufflers.gym.entities.Leaders.*;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
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
        cities.put("ViridianCity", gym()
                .warpId(4)
                .leaderOld(Giovanni)
                .pokemonRangeLevel(viridianRangeLevel)
                .name(CELADON_GYM)
                .leader(Erika)
                .build());

        Integer[] pewterRangeLevel = {12, 14};
        cities.put("PewterCity", gym()
                .warpId(2)
                .leaderOld(Brock)
                .pokemonRangeLevel(pewterRangeLevel)
                .name(SAFFRON_GYM)
                .leader(Sabrina).build());

        Integer[] ceruleanRangeLevel = {24, 29};
        cities.put("CeruleanCity", gym()
                .warpId(3)
                .leaderOld(Misty)
                .pokemonRangeLevel(ceruleanRangeLevel)
                .name(VIRIDIAN_GYM)
                .leader(Giovanni).build());

        gymShuffler.process(cities);

        String outputPath = System.getProperty("user.home") + "/Projects/randomizer-output/";

        for (Map.Entry<String, Object> entry : cities.entrySet()) {
            String cityName = entry.getKey();
            Object gym = entry.getValue();
            String expectedCityAsmFile = new String(readAllBytes(get(getClass().getResource("../maps/objects/" + cityName + "-shuffled.txt").toURI())));
            String cityAsmFile = new String(readAllBytes(get(outputPath + "data/maps/objects/" + cityName + ".asm")));
            assertThat(cityAsmFile).isEqualTo(expectedCityAsmFile);

            String gymName = ((Gym) gym).getName().getName();
            String expectedGymAsmFile = new String(readAllBytes(get(getClass().getResource("../maps/objects/" + gymName + "-shuffled.txt").toURI())));
            String gymAsmFile = new String(readAllBytes(get(outputPath + "data/maps/objects/" + gymName + ".asm")));
            assertThat(gymAsmFile).isEqualTo(expectedGymAsmFile);

            String expectedGymScriptAsmFile = new String(readAllBytes(get(getClass().getResource("../scripts/" + gymName + "-shuffled.txt").toURI())));
            String gymScriptAsmFile = new String(readAllBytes(get(outputPath + "scripts/" + gymName + ".asm")));
            assertThat(gymScriptAsmFile).isEqualToIgnoringNewLines(expectedGymScriptAsmFile);
        }

        String expectedTrainerPartiesAsmFile = new String(readAllBytes(get(getClass().getResource("../maps/objects/trainer_parties-shuffled.txt").toURI())));
        String trainerPartiesAsmFile = new String(readAllBytes(get(outputPath + "data/trainers/parties.asm")));
        assertThat(trainerPartiesAsmFile).isEqualTo(expectedTrainerPartiesAsmFile);
    }

    @Test
    public void shouldShuffleGyms() {
        Integer[] viridianRangeLevel = {42, 50};
        int viridianWarpId = 4;

        Map<String, Object> gyms = gymShuffler.shuffle();

        Gym newViridianGym = gym().warpId(viridianWarpId).leader(Erika).leaderOld(Giovanni).pokemonRangeLevel(viridianRangeLevel).name(CELADON_GYM).build();
        assertThat(gyms.get("ViridianCity")).isEqualToComparingFieldByField(newViridianGym);
    }
}

