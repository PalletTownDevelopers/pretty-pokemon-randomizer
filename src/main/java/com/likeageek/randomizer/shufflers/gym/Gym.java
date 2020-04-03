package com.likeageek.randomizer.shufflers.gym;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Gym {
    private Gyms name;
    private int warpId;
    private Leaders leaderOld;
    private Leaders leader;
    private Integer[] pokemonRangeLevel;
    private Map<String, List<Integer>> trainers;

    public Gyms getName() {
        return name;
    }

    public void setName(Gyms name) {
        this.name = name;
    }

    int getWarpId() {
        return warpId;
    }

    void setWarpId(int warpId) {
        this.warpId = warpId;
    }

    Leaders getLeaderOld() {
        return leaderOld;
    }

    void setLeaderOld(Leaders leaderOld) {
        this.leaderOld = leaderOld;
    }

    Leaders getLeader() {
        return leader;
    }

    void setLeader(Leaders leader) {
        this.leader = leader;
    }

    Map<String, List<Integer>> getTrainers() {
        return trainers;
    }

    void setTrainers(Map<String, List<Integer>> trainers) {
        this.trainers = trainers;
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
