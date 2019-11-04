package com.likeageek.randomizer.shufflers.gym;

public class Gym {
    private Gyms name;
    private int warpId;
    private Trainers trainer;
    private int[] pokemonRangeLevel;

    public Gyms getName() {
        return name;
    }

    public void setName(Gyms name) {
        this.name = name;
    }

    public int getWarpId() {
        return warpId;
    }

    public void setWarpId(int warpId) {
        this.warpId = warpId;
    }

    public Trainers getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainers trainer) {
        this.trainer = trainer;
    };

    public int[] getPokemonRangeLevel() {
        return pokemonRangeLevel;
    }

    public void setPokemonRangeLevel(int[] pokemonRangeLevel) {
        this.pokemonRangeLevel = pokemonRangeLevel;
    }
}
