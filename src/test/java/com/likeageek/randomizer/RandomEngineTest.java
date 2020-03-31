package com.likeageek.randomizer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RandomEngineTest {

    private RandomEngine randomEngine = new RandomEngine(42);

    @Test
    void shouldRandom() {
        List<Object> random = randomEngine.random(asList(1, 2, 3, 4));
        assertThat(random).isEqualTo(asList(4, 2, 1, 3));
    }

    @Test
    void shouldRandom2() {
        randomEngine.setTest(true);
        Object random = randomEngine.randomBetweenRangeValues(asList(1, 2));
        assertThat(random).isEqualTo(2);
    }
}