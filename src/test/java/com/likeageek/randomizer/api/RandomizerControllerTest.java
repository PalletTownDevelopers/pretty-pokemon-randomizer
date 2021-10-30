package com.likeageek.randomizer.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RandomizerControllerTest {

    @Test
    public void generateRom() {
        String romParameters = "{\n" +
                "    \"seed\": \"42\",\n" +
                "    \"timestamp\": 123456789,\n" +
                "    \"option\": {\n" +
                "      \"arena\" : true\n" +
                "    },\n" +
                "    \"customization\": {\n" +
                "      \"yellowSprite\": true\n" +
                "    }\n" +
                "  }";
        given()
                .pathParam("seed", "42")
                .header("Content-Type", "application/json")
                .body(romParameters)
                .when().get("/v1/pretty-pokemon-randomizer/generate-rom/{seed}")
                .then()
                .statusCode(200)
                .body(is("a randomized rom"));
    }
}
