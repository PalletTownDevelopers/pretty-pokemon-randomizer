package com.likeageek.randomizer.api;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RomRepository implements PanacheRepository<Rom> {
}
