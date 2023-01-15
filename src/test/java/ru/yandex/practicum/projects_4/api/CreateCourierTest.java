package ru.yandex.practicum.projects_4.api;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.courier.*;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest extends GenerateCourier {

    @Before
    public void installSpecification() {
        Specifications.requestSpec();
    }

    @Test
    public void successCreateCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        SuccessCreateCourier newCourier = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().as(SuccessCreateCourier.class);

        boolean actualResponse = newCourier.getOk();
        assertTrue(actualResponse);

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

        UnSuccessCreateDuplicateCourier duplicateCourier = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(409)
                .extract().as(UnSuccessCreateDuplicateCourier.class);

        String expectedMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualMessage = duplicateCourier.getMessage();

        assertEquals(expectedMessage, actualMessage);

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

        UnSuccessCreateCourierNoLoginOrNoPassword noLoginCourier = given()
                .body(newNoLoginCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateCourierNoLoginOrNoPassword.class);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = noLoginCourier.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void createNoPasswordCourierTest() {
        CreateCourier newNoPasswordCourier = buildNewNoPasswordRandomCourier();

        UnSuccessCreateCourierNoLoginOrNoPassword noPasswordCourier = given()
                .body(newNoPasswordCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateCourierNoLoginOrNoPassword.class);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = noPasswordCourier.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}