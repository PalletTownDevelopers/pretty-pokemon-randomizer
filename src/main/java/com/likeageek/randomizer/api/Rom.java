package com.likeageek.randomizer.api;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;

@Entity(name = "ppr_rom")
public class Rom extends PanacheEntity {
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
