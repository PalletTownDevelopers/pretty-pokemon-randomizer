package com.likeageek.randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomEngine {
    public List<String> random(List<String> input, int seed) {
        List<String> output = new ArrayList<>(input);
        Collections.shuffle(output, new Random(seed));
        return output;
    }
}
