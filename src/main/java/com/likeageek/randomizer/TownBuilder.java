package com.likeageek.randomizer;

public final class TownBuilder {
    private Town town;

    private TownBuilder() {
        town = new Town();
    }

    public static TownBuilder town() {
        return new TownBuilder();
    }

    public TownBuilder name(Towns name) {
        town.setName(name);
        return this;
    }

    public TownBuilder arena(String arena) {
        town.setArena(arena);
        return this;
    }

    public Town build() {
        return town;
    }
}
