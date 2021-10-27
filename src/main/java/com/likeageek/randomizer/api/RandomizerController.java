package com.likeageek.randomizer.api;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/pretty-pokemon-randomizer")
public class RandomizerController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/generate-rom/{seed}")
    public String generateRom(@PathParam String seed) {
        return String.format("your seed is: %s", seed);
    }
}