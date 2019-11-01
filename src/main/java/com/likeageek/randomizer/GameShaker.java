package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.IShuffler;
import com.likeageek.randomizer.shufflers.empty.EmptyShuffler;
import com.likeageek.randomizer.shufflers.gym.GymShuffler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GameShaker {
    private final IFileManager asmFileManager;
    private Configuration configuration;
    private List<IShuffler> shufflers;

    public GameShaker(Configuration configuration, IFileManager asmFileManager) {
        this.configuration = configuration;
        this.asmFileManager = asmFileManager;
    }

    public void init() throws IOException {
        asmFileManager.copyGame();
        this.shufflers = new ArrayList<>(asList(
                new GymShuffler(asmFileManager),
                new EmptyShuffler()
        ));
    }

    public void shake() {
        this.shufflers.forEach(shuffler -> {
            Map<String, String> shuffledArenas = shuffler.shuffle(configuration.getSeed());
            shuffler.process(shuffledArenas);
            shuffler.getResult().forEach((city, gym) -> {
                System.out.println(city + ":" + gym + "\r\n");
            });
        });
    }
}
