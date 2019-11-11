package com.likeageek.randomizer.shufflers.empty;

import com.likeageek.randomizer.shufflers.IShuffler;

import java.util.HashMap;
import java.util.Map;

public class EmptyShuffler implements IShuffler {

    public EmptyShuffler() {
        System.out.println("empty shuffler");
    }

    @Override
    public Map<String, Object> shuffle() {
        return new HashMap<>();
    }

    @Override
    public void process(Map<String, Object> shuffledEntries) {
    }
}
