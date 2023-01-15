package ru.yandex.practicum.projects_4.api;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.courier.requests.CreateCourier;
import ru.yandex.practicum.projects_4.model.courier.succcesResponses.SuccessLoginCourier;
import ru.yandex.practicum.projects_4.model.courier.unSucccesResponses.UnSuccessCreateOrLogin;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest extends CourierGenerator {

    @Before
    public void installSpecification() {
        Specifications.requestSpec();
    }

    @Test
    public void succesLoginCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        SuccessLoginCourier loginCourierResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(SuccessLoginCourier.class);

        assertNotNull(loginCourierResponse.getId());

        given()
                .when()
                .delete("/api/v1/courier/" + loginCourierResponse.getId());

    }

    @Test
    public void loginCourierNoLoginTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        newRandomCourier.setLogin("");

        UnSuccessCreateOrLogin noLoginResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = noLoginResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void loginCourierNoPasswordTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        newRandomCourier.setPassword("");

        UnSuccessCreateOrLogin noPasswordResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = noPasswordResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void loginCourierNotValidDataTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        newRandomCourier.setLogin("Courier_" + UUID.randomUUID());

        UnSuccessCreateOrLogin noValidDataResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .statusCode(404)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = noValidDataResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}