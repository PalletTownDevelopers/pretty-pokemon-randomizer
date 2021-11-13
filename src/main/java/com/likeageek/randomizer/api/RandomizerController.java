package com.likeageek.randomizer.api;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

@Path("/v1/pretty-pokemon-randomizer")
public class RandomizerController {
    @Inject
    RomService service;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/generate-rom")
    public Response generateRom(RomInformation romInformation) throws URISyntaxException, IOException, InterruptedException {
        File romGenerated = service.generate(romInformation);
        service.saveInformation(romInformation);
        Response.ResponseBuilder response = Response.ok((Object) romGenerated);
        response.header("Content-Disposition", "attachment;filename=" + romGenerated.getName());
        return response.build();
    }
}