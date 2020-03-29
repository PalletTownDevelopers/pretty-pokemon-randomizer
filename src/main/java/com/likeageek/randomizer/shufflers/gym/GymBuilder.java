package com.likeageek.randomizer.shufflers.gym;

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

    public GymBuilder trainer(Leaders trainer) {
        gym.setLeader(trainer);
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
