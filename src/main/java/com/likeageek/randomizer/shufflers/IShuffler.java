package com.likeageek.randomizer.shufflers;

import java.util.Map;

public interface IShuffler {
    Map<String, Object> shuffle();

    void process(Map<String, Object> shuffledEntries);
}
