package com.likeageek.randomizer.api;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity(name = "ppr_rom")
public class Rom extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "pprRomIdSequence",
            sequenceName = "ppr_rom_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ppr_rom_id_seq")
    public Integer id;
    private Integer seed;

    public Rom(Integer seed) {
        this.seed = seed;
    }

    public Rom() {

    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Integer getSeed() {
        return seed;
    }
}
