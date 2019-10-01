package com.likeageek.randomizer.shufflers;

import java.io.IOException;
import java.util.Map;

public interface IShuffler {
    Map<String, String> shuffle(int seed);

    void process(Map<String, String> shuffledEntries) throws IOException;
}
