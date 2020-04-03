package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.AsmFileParser;
import com.likeageek.randomizer.FakeAsmFileManager;
import com.likeageek.randomizer.RandomEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.Gyms.CELADON_GYM;
import static com.likeageek.randomizer.shufflers.gym.Gyms.SAFFRON_GYM;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class LeaderTrainersProcessorTest {
    private LeaderTrainersProcessor leaderTrainersProcessor;

    @BeforeEach
    public void init() {
        RandomEngine randomEngine = new RandomEngine(3297392);
        randomEngine.setTest(true);
        leaderTrainersProcessor = new LeaderTrainersProcessor(new FakeAsmFileManager(), new AsmFileParser(), randomEngine);
    }

    @Test
    public void shouldReadGymTrainersAndTransformNameToCamelCase() {
        Map<String, List<Integer>> trainers = leaderTrainersProcessor.getTrainers(CELADON_GYM);
        assertThat(trainers.size()).isEqualTo(4);
        assertThat(trainers.get("Beauty")).isEqualTo(asList(1, 2, 3));
        assertThat(trainers.get("Lass")).isEqualTo(asList(17, 18));
        assertThat(trainers.get("CooltrainerF")).isEqualTo(singletonList(1));
        assertThat(trainers.get("JrTrainerF")).isEqualTo(singletonList(11));
    }

    @Test
    public void shouldReadGymTrainersAndReplacePsychicTr_whenSaffronGym() {
        Map<String, List<Integer>> trainers = leaderTrainersProcessor.getTrainers(SAFFRON_GYM);
        assertThat(trainers.size()).isEqualTo(2);
        assertThat(trainers.get("Channeler")).isEqualTo(asList(22, 23, 24));
        assertThat(trainers.get("Psychic")).isEqualTo(singletonList(4));
    }
}