package com.likeageek.randomizer;

public class Configuration {
    private long seed;
    private String pokemonDirectory;
    private String outputDirectory;

    long getSeed() {
        return seed;
    }

    void setSeed(long seed) {
        this.seed = seed;
    }

    String getPokemonDirectory() {
        return pokemonDirectory;
    }

    void setPokemonDirectory(String pokemonDirectory) {
        this.pokemonDirectory = pokemonDirectory;
    }

    String getOutputDirectory() {
        return outputDirectory;
    }

    void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
