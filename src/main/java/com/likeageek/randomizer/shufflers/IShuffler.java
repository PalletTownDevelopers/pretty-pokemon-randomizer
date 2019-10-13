package com.likeageek.randomizer.shufflers;

import java.util.Map;

public interface IShuffler {
    Map<String, String> shuffle(long seed);

    void process(Map<String, String> shuffledEntries);

    Map<String, String> getResult();
}
