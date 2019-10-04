package com.likeageek.randomizer.shufflers;

import java.util.HashMap;
import java.util.Map;

public class EmptyShuffler implements IShuffler {

    public EmptyShuffler() {
        System.out.println("empty shuffler");
    }

    @Override
    public Map<String, String> shuffle(int seed) {
        return null;
    }

    @Override
    public void process(Map<String, String> shuffledEntries) {

    }

    @Override
    public Map<String, String> getResult() {
        return new HashMap<>();
    }
}
