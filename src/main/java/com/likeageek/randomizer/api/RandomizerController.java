package com.likeageek.randomizer.api;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Path("/v1/pretty-pokemon-randomizer")
public class RandomizerController {
    @Inject
    RomService service;
    //TODO a pokered-source folder

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/generate-rom/{seed}")
    public Response generateRom(@PathParam String seed) throws URISyntaxException {
        //TODO copy pokered-source folder to pokered-copy
        //TODO generate rom with params into pokered-copy
        //TODO move rom.gbc to download-folder
        //TODO delete folder pokered-copy
        service.saveRom(seed);
        File file = fileFromResources("rom.gbc");
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=" + file.getName());
        return response.build();
    }

    private File fileFromResources(String fileName) throws URISyntaxException {
        URL file = getClass().getClassLoader().getResource(fileName);
        if (file == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        return new File(file.toURI());
    }
}