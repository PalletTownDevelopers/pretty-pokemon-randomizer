package com.likeageek.randomizer.shufflers.gym;

import java.util.List;
import java.util.Map;

public final class GymBuilder {
    private Gym gym;

    private GymBuilder() {
        gym = new Gym();
    }

    public static GymBuilder gym() {
        return new GymBuilder();
    }

    public GymBuilder name(Gyms name) {
        gym.setName(name);
        return this;
    }

    public GymBuilder warpId(int warpId) {
        gym.setWarpId(warpId);
        return this;
    }

    public GymBuilder leader(Leaders leader) {
        gym.setLeader(leader);
        return this;
    }

    public GymBuilder trainers(Map<String, List<Integer>> trainers) {
        gym.setTrainers(trainers);
        return this;
    }

    public GymBuilder pokemonRangeLevel(Integer[] pokemonRangeLevel) {
        gym.setPokemonRangeLevel(pokemonRangeLevel);
        return this;
    }

    public Gym build() {
        return gym;
    }
}
