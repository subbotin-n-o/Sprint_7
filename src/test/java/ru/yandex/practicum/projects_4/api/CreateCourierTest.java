package ru.yandex.practicum.projects_4.api;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.CreateCourier;
import ru.yandex.practicum.projects_4.model.SuccessLoginCourier;

import static io.restassured.RestAssured.given;

public class CreateCourierTest extends GenerateCourier {

    @Before
    public void installSpecification() {
        Specifications.requestSpec();
    }

    @Test
    public void successCreateCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat().statusCode(201);

        SuccessLoginCourier successLoginNewCourier = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().as(SuccessLoginCourier.class);

        given()
                .when()
                .delete("/api/v1/courier/" + successLoginNewCourier.getId())
                .then();
    }

    @Test
    public void createDuplicateCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat().statusCode(409);

        SuccessLoginCourier successLoginNewCourier = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().as(SuccessLoginCourier.class);

        given()
                .when()
                .delete("/api/v1/courier/" + successLoginNewCourier.getId())
                .then();
    }

    @Test
    public void createNoLoginCourierTest() {
        CreateCourier newNoLoginCourier = buildNewNoLoginRandomCourier();

        given()
                .body(newNoLoginCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    public void createNoPasswordCourierTest() {
        CreateCourier newNoPasswordCourier = buildNewNoPasswordRandomCourier();

        given()
                .body(newNoPasswordCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat().statusCode(400);
    }

}