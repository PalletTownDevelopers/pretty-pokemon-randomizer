package com.likeageek.randomizer.shufflers.gym;

public final class CityBuilder {
    private City city;

    private CityBuilder() {
        city = new City();
    }

    public static CityBuilder city() {
        return new CityBuilder();
    }

    public CityBuilder name(Cities name) {
        city.setName(name);
        return this;
    }

    public CityBuilder arena(Gym gym) {
        city.setGym(gym);
        return this;
    }

    public City build() {
        return city;
    }
}
