package com.likeageek.randomizer.shufflers.gym;

public enum Gyms {
    VIRIDIAN_GYM("ViridianGym"),
    VERMILION_GYM("VermilionGym"),
    CERULEAN_GYM("CeruleanGym"),
    PEWTER_GYM("PewterGym"),
    CELADON_GYM("CeladonGym"),
    FUCHSIA_GYM("FuchsiaGym"),
    SAFFRON_GYM("SaffronGym"),
    CINNABAR_GYM("CinnabarGym");

    private final String name;

    Gyms(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
