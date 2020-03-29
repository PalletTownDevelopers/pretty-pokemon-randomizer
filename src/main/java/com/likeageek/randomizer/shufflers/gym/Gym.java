package com.likeageek.randomizer.shufflers.gym;

import java.util.Arrays;

public class Gym {
    private Gyms name;
    private int warpId;
    private Leaders leader;
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

    Leaders getLeader() {
        return leader;
    }

    void setLeader(Leaders leader) {
        this.leader = leader;
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
                ", leader=" + leader +
                ", pokemonRangeLevel=" + Arrays.toString(pokemonRangeLevel) +
                '}';
    }
}
