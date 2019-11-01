package com.likeageek.randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

public class RandomEngine {
    public List<String> random(List<String> values, long seed) {
        List<String> randomizedValues = new ArrayList<>(values);
        shuffle(randomizedValues, new Random(seed));
        return randomizedValues;
    }
}
