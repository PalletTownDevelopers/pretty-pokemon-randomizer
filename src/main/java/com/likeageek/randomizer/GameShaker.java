package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.IShuffler;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GameShaker {
    private final IFileManager asmFileManager;
    private final IFileParser asmFileParser;
    private final IRandomEngine randomEngine;
    private List<IShuffler> shufflers;

    public GameShaker(Configuration configuration,
                      IFileManager asmFileManager,
                      IFileParser asmFileParser) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
        this.randomEngine = new RandomEngine(configuration.getSeed());
    }

    public void init() throws IOException {
        asmFileManager.copyGame();
        this.shufflers = new ArrayList<>(asList(
                new GymShuffler(asmFileManager, asmFileParser, randomEngine)
        ));
    }

    public void shake() {
        this.shufflers.forEach(shuffler -> {
            Map<String, Object> shuffledGameObject = shuffler.shuffle();
            shuffler.process(shuffledGameObject);
            shuffledGameObject.forEach((city, gym) -> {
                System.out.println(city + ":" + gym.toString() + "\r\n");
            });
        });
    }
}
