package com.likeageek.randomizer.api;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RomRepository implements PanacheRepositoryBase<Rom, Integer> {
}
