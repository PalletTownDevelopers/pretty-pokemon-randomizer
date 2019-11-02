package com.likeageek.randomizer.shufflers;

import java.util.Map;

public interface IShuffler {
    Map<String, Object> shuffle(long seed);

    void process(Map<String, Object> shuffledEntries);

    Map<String, Object> getResult();
}
