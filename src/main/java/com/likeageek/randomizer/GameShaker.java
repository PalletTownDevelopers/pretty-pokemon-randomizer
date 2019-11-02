package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.IShuffler;
import com.likeageek.randomizer.shufflers.empty.EmptyShuffler;
import com.likeageek.randomizer.shufflers.gym.Gym;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GameShaker {
    private final IFileManager asmFileManager;
    private final IFileParser asmFileParser;
    private Configuration configuration;
    private List<IShuffler> shufflers;

    public GameShaker(Configuration configuration,
                      IFileManager asmFileManager,
                      IFileParser asmFileParser) {
        this.configuration = configuration;
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
    }

    public void init() throws IOException {
        asmFileManager.copyGame();
        this.shufflers = new ArrayList<>(asList(
                new GymShuffler(asmFileManager, asmFileParser),
                new EmptyShuffler()
        ));
    }

    public void shake() {
        this.shufflers.forEach(shuffler -> {
            Map<String, Object> shuffledArenas = shuffler.shuffle(configuration.getSeed());
            shuffler.process(shuffledArenas);
            shuffler.getResult().forEach((city, gym) -> {
                System.out.println(city + ":" + ((Gym) gym).getName() + ":warpId:" + ((Gym) gym).getWarpId() + "\r\n");
            });
        });
    }
}
