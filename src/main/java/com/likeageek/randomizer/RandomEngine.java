package com.likeageek.randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;
import static java.util.Collections.shuffle;

public class RandomEngine implements IRandomEngine {
    private long seed;
    private boolean test;

    public RandomEngine(long seed) {
        this.seed = seed;
        this.test = false;
    }

    @Override
    public List<Object> random(List<Object> values) {
        List<Object> randomizedValues = new ArrayList<>(values);
        shuffle(randomizedValues, new Random(seed));
        return randomizedValues;
    }

    @Override
    public Object randomBetweenRangeValues(List<Integer> values) {
        Random random;
        if (test) {
            random = new Random(42);
        } else {
            random = new Random();
        }
        return random.nextInt((parseInt(values.get(1).toString()) - parseInt(values.get(0).toString())) + 1) + parseInt(values.get(0).toString());
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
