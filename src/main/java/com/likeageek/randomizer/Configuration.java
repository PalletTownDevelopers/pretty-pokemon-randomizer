package com.likeageek.randomizer;

public class Configuration {
    private long seed;
    private String pokemonDirectory;
    private String outputDirectory;

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public String getPokemonDirectory() {
        return pokemonDirectory;
    }

    public void setPokemonDirectory(String pokemonDirectory) {
        this.pokemonDirectory = pokemonDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
