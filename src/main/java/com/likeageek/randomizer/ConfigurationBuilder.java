package com.likeageek.randomizer;

public final class ConfigurationBuilder {
    private Configuration configuration;

    private ConfigurationBuilder() {
        configuration = new Configuration();
    }

    public static ConfigurationBuilder configuration() {
        return new ConfigurationBuilder();
    }

    public ConfigurationBuilder seed(int seed) {
        configuration.setSeed(seed);
        return this;
    }

    public ConfigurationBuilder pokemonPath(String pokemonPath) {
        configuration.setPokemonPath(pokemonPath);
        return this;
    }

    public ConfigurationBuilder outputPath(String outputPath) {
        configuration.setOutputPath(outputPath);
        return this;
    }

    public Configuration build() {
        return configuration;
    }
}
