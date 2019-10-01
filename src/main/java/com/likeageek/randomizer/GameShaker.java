package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.ArenaShuffler;

import java.io.IOException;
import java.util.Map;

public class GameShaker {
    private Configuration configuration;

    public GameShaker(Configuration configuration) {
        this.configuration = configuration;
    }

    public void shake() throws IOException {
        ArenaShuffler arenaShuffler = new ArenaShuffler(new AsmFileManager(configuration.getPokemonDirectory(), configuration.getOutputDirectory()));
        System.out.println("arena shuffler");
        Map<String, String> shuffledArenas = arenaShuffler.shuffle(configuration.getSeed());
        arenaShuffler.process(shuffledArenas);
        arenaShuffler.getShuffledArenas().values().forEach(arena -> {
            System.out.println(arena + "\r\n");
        });
    }
}
