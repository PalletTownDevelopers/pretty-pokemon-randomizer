package com.likeageek.randomizer.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class RomService {
    @Inject
    RomRepository repository;

    @Transactional
    public void saveRom(String seed) {
        Rom entity = new Rom(Integer.parseInt(seed));
        repository.persist(entity);
    }
}
