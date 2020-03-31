package com.likeageek.randomizer.shufflers.example;

import com.likeageek.randomizer.shufflers.IShuffler;

import java.util.HashMap;
import java.util.Map;

public class ExampleShuffler implements IShuffler {

    public ExampleShuffler() {
        System.out.println("empty example shuffler");
    }

    @Override
    public Map<String, Object> shuffle() {
        return new HashMap<>();
    }

    @Override
    public void process(Map<String, Object> shuffledEntries) {
    }
}
