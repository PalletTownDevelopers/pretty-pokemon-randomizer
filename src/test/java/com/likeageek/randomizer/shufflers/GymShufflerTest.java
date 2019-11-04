package com.likeageek.randomizer.shufflers;

import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.shufflers.gym.Gym;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;
import com.likeageek.randomizer.shufflers.gym.Gyms;
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

    @BeforeEach
    public void init() {
        gymShuffler = new GymShuffler(new FakeAsmFileManager(), new AsmFileParser());
    }

    @Test
    public void shouldProcessGymsWithWarpID() throws URISyntaxException, IOException {
        Map<String, Object> cities = new HashMap<>();
        cities.put("ViridianCity", gym().warpId(4).name(Gyms.valueOf("CINNABAR_GYM")).build());
        cities.put("VermilionCity", gym().warpId(3).name(Gyms.valueOf("SAFFRON_GYM")).build());
        cities.put("CeruleanCity", gym().warpId(3).name(Gyms.valueOf("CERULEAN_GYM")).build());
        cities.put("PewterCity", gym().warpId(2).name(Gyms.valueOf("VIRIDIAN_GYM")).build());
        cities.put("CeladonCity", gym().warpId(6).name(Gyms.valueOf("VERMILION_GYM")).build());
        cities.put("FuchsiaCity", gym().warpId(5).name(Gyms.valueOf("CELADON_GYM")).build());
        cities.put("SaffronCity", gym().warpId(2).name(Gyms.valueOf("FUCHSIA_GYM")).build());
        cities.put("CinnabarIsland", gym().warpId(1).name(Gyms.valueOf("PEWTER_GYM")).build());

        gymShuffler.process(cities);

        for (Map.Entry<String, Object> entry : cities.entrySet()) {
            String cityName = entry.getKey();
            Object gym = entry.getValue();
            String expectedCityAsmFile = new String(readAllBytes(get(getClass().getResource("../" + cityName + "-shuffled.txt").toURI())));
            String cityAsmFile = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-output/data/mapObjects/" + cityName + ".asm")));
            assertThat(cityAsmFile).isEqualTo(expectedCityAsmFile);

            String gymName = ((Gym) gym).getName().getName();
            String expectedGymAsmFile = new String(readAllBytes(get(getClass().getResource("../" + gymName + "-shuffled.txt").toURI())));
            String gymAsmFile = new String(readAllBytes(get("/home/likeageek/Projects/randomizer-output/data/mapObjects/" + gymName + ".asm")));
            assertThat(gymAsmFile).isEqualTo(expectedGymAsmFile);
        }
    }

    @Test
    public void shouldShuffleGyms() {
        Map<String, Object> gyms = gymShuffler.shuffle(3297392);

        Gym newViridianGym = gym().warpId(4).trainer(Erika).pokemonRangeLevel(new int[]{24, 29}).name(CELADON_GYM).build();
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

