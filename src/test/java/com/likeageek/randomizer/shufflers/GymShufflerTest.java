package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.RandomEngine;
import com.likeageek.randomizer.shufflers.gym.Gym;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;
import com.likeageek.randomizer.shufflers.gym.Trainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.Gyms.CELADON_GYM;
import static com.likeageek.randomizer.shufflers.gym.Trainers.Erika;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GymShufflerTest {
    private GymShuffler gymShuffler;
    private String outputPath = "/home/likeageek/Projects/randomizer-output/data/";

    @BeforeEach
    public void init() {
        RandomEngine randomEngine = new RandomEngine(3297392);
        randomEngine.setTest(true);
        gymShuffler = new GymShuffler(new FakeAsmFileManager(), new AsmFileParser(), randomEngine);
    }

    @Test
    public void shouldProcessGymsWithWarpID() throws URISyntaxException, IOException {
        Integer[] viridianRangeLevel = {42, 50};
        int viridianWarpId = 4;
        Trainers celadonTrainer = Erika;

        Map<String, Object> cities = new HashMap<>();
        cities.put("ViridianCity", gym().warpId(viridianWarpId).trainer(celadonTrainer).pokemonRangeLevel(viridianRangeLevel).name(CELADON_GYM).build());

        gymShuffler.process(cities);

        for (Map.Entry<String, Object> entry : cities.entrySet()) {
            String cityName = entry.getKey();
            Object gym = entry.getValue();
            String expectedCityAsmFile = new String(readAllBytes(get(getClass().getResource("../" + cityName + "-shuffled.txt").toURI())));
            String cityAsmFile = new String(readAllBytes(get(outputPath + "mapObjects/" + cityName + ".asm")));
            assertThat(cityAsmFile).isEqualTo(expectedCityAsmFile);

            String gymName = ((Gym) gym).getName().getName();
            String expectedGymAsmFile = new String(readAllBytes(get(getClass().getResource("../" + gymName + "-shuffled.txt").toURI())));
            String gymAsmFile = new String(readAllBytes(get(outputPath + "mapObjects/" + gymName + ".asm")));
            assertThat(gymAsmFile).isEqualTo(expectedGymAsmFile);
        }

        String expectedTrainerPartiesAsmFile = new String(readAllBytes(get(getClass().getResource("../trainer_parties-shuffled.txt").toURI())));
        String trainerPartiesAsmFile = new String(readAllBytes(get(outputPath + "/trainer_parties.asm")));
        assertThat(trainerPartiesAsmFile).isEqualTo(expectedTrainerPartiesAsmFile);
    }

    @Test
    public void shouldShuffleGyms() {
        Integer[] viridianRangeLevel = {42, 50};
        int viridianWarpId = 4;
        Trainers celadonTrainer = Erika;

        Map<String, Object> gyms = gymShuffler.shuffle();

        Gym newViridianGym = gym().warpId(viridianWarpId).trainer(celadonTrainer).pokemonRangeLevel(viridianRangeLevel).name(CELADON_GYM).build();
        assertThat(gyms.get("ViridianCity")).isEqualToComparingFieldByField(newViridianGym);
    }

    static class FakeAsmFileManager implements IFileManager {

        @Override
        public void write(String filePath, String asmSourceCode) throws IOException {
            FileWriter fileWriter = new FileWriter("/home/likeageek/Projects/randomizer-output/" + filePath + ".asm");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(asmSourceCode);
            printWriter.close();
        }

        @Override
        public String read(String filePath) throws IOException {
            return new String(readAllBytes(get("/home/likeageek/Projects/randomizer-cache/" + filePath + ".asm")));
        }

        @Override
        public void copyGame() throws IOException {

        }
    }
}

