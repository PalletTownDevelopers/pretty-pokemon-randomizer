package com.likeageek.randomizer;

public class Configuration {
    private int seed;
    private String pokemonDirectory;
    private String outputDirectory;

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setPokemonDirectory(String pokemonDirectory) {
        this.pokemonDirectory = pokemonDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public int getSeed() {
        return seed;
    }

    public String getPokemonDirectory() {
        return pokemonDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }
}
