package io.strimzi.kafka.sps.dvm;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.strimzi.kafka.sps.dvm.system.System;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class SystemResourceTest {

    @Test
    public void get() {
        given()
                .when().get("/systems")
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        System system = createSystem();
        System saved = given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(201)
                .extract().as(System.class);
        System got = given()
                .when()
                .get("/systems/{name}", saved.name())
                .then()
                .statusCode(200)
                .extract().as(System.class);
        assertThat(saved).isEqualTo(got);
    }

    @Test
    public void getByNameNotFound() {
        given()
                .when()
                .get("/systems/{name}", RandomStringUtils.randomAlphabetic(10))
                .then()
                .statusCode(404);
    }

    @Test
    public void post() {
        System system = createSystem();
        System saved = given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(201)
                .extract().as(System.class);
        assertThat(saved.name())
                .isNotNull()
                .isEqualTo(system.name());
    }

    @Test
    public void postFailNoName() {
        System system = new System(null);
        given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(400);
    }

    @Test
    public void postFailDuplicateName() {
        System system = createSystem();
        System saved = given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(201)
                .extract().as(System.class);
        given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(400);
    }

    @Test
    public void put() {
        System system = createSystem();
        System saved = given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(201)
                .extract().as(System.class);
        System newSystem = new System(RandomStringUtils.randomAlphabetic(10));
        given()
                .contentType(ContentType.JSON)
                .body(newSystem)
                .put("/systems/{name}", saved.name())
                .then()
                .statusCode(204);
    }

    @Test
    public void putNoNameFound() {
        System system = createSystem();
        System saved = given()
                .contentType(ContentType.JSON)
                .body(system)
                .post("/systems")
                .then()
                .statusCode(201)
                .extract().as(System.class);
        System newSystem = new System(RandomStringUtils.randomAlphabetic(10));
        given()
                .contentType(ContentType.JSON)
                .body(newSystem)
                .put("/systems/{name}", newSystem.name())
                .then()
                .statusCode(400);
    }

    private System createSystem() {
        System system = new System(RandomStringUtils.randomAlphabetic(10));
        return system;
    }

}