package com.likeageek.randomizer;

public class Configuration {
    private int seed;
    private String pokemonPath;
    private String outputPath;

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setPokemonPath(String pokemonPath) {
        this.pokemonPath = pokemonPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    public int getSeed() {
        return seed;
    }

    public String getPokemonPath() {
        return pokemonPath;
    }

    public String getOutputPath() {
        return outputPath;
    }
}
