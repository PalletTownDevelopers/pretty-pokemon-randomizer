package com.likeageek.randomizer;

import com.likeageek.randomizer.shufflers.IShuffler;
import com.likeageek.randomizer.shufflers.arena.ArenaShuffler;
import com.likeageek.randomizer.shufflers.empty.EmptyShuffler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GameShaker {
    private Configuration configuration;
    private List<IShuffler> shufflers;

    public GameShaker(Configuration configuration) {
        this.configuration = configuration;
    }

    public void shake() {
        this.shufflers.forEach(suffler -> {
            Map<String, String> shuffledArenas = suffler.shuffle(configuration.getSeed());
            suffler.process(shuffledArenas);
            suffler.getResult().values().forEach(arena -> {
                System.out.println(arena + "\r\n");
            });
        });
    }

    public void load() {
        this.shufflers = new ArrayList<>(asList(
                new ArenaShuffler(new AsmFileManager(configuration.getPokemonDirectory(), configuration.getOutputDirectory())),
                new EmptyShuffler()
        ));
    }
}
