package com.likeageek.randomizer;

public final class ConfigurationBuilder {
    private Configuration configuration;

    private ConfigurationBuilder() {
        configuration = new Configuration();
    }

    static ConfigurationBuilder configuration() {
        return new ConfigurationBuilder();
    }

    ConfigurationBuilder seed(long seed) {
        configuration.setSeed(seed);
        return this;
    }

    ConfigurationBuilder pokemonDirectory(String pokemonPath) {
        configuration.setPokemonDirectory(pokemonPath);
        return this;
    }

    ConfigurationBuilder outputDirectory(String outputPath) {
        configuration.setOutputDirectory(outputPath);
        return this;
    }

    public Configuration build() {
        return configuration;
    }
}
