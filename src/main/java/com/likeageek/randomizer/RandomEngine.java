package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.gym.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

public class RandomEngine {
    public List<City> random(List<City> values, long seed) {
        List<City> randomizedValues = new ArrayList<>(values);
        shuffle(randomizedValues, new Random(seed));
        return randomizedValues;
    }
}
