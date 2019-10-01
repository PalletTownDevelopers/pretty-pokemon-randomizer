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

    public ConfigurationBuilder pokemonDirectory(String pokemonPath) {
        configuration.setPokemonDirectory(pokemonPath);
        return this;
    }

    public ConfigurationBuilder outputDirectory(String outputPath) {
        configuration.setOutputDirectory(outputPath);
        return this;
    }

    public Configuration build() {
        return configuration;
    }
}
