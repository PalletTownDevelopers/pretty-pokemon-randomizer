package com.likeageek.randomizer.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RandomizerControllerTest {


    @Test
    public void generateRom() {
        given()
                .pathParam("seed", "42")
                .when().get("/v1/pretty-pokemon-randomizer/generate-rom/{seed}")
                .then()
                .statusCode(200)
                .body(is("your seed is: 42"));
    }
}
