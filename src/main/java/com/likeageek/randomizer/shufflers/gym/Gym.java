package com.likeageek.randomizer.shufflers.gym;

import java.util.Arrays;

public class Gym {
    private Gyms name;
    private int warpId;
    private Trainers trainer;
    private Integer[] pokemonRangeLevel;

    public Gyms getName() {
        return name;
    }

    public void setName(Gyms name) {
        this.name = name;
    }

    public int getWarpId() {
        return warpId;
    }

    void setWarpId(int warpId) {
        this.warpId = warpId;
    }

    Trainers getTrainer() {
        return trainer;
    }

    void setTrainer(Trainers trainer) {
        this.trainer = trainer;
    }

    Integer[] getPokemonRangeLevel() {
        return pokemonRangeLevel;
    }

    void setPokemonRangeLevel(Integer[] pokemonRangeLevel) {
        this.pokemonRangeLevel = pokemonRangeLevel;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "name=" + name +
                ", warpId=" + warpId +
                ", trainer=" + trainer +
                ", pokemonRangeLevel=" + Arrays.toString(pokemonRangeLevel) +
                '}';
    }
}
