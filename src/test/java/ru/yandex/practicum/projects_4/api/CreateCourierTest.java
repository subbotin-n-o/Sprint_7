package ru.yandex.practicum.projects_4.api;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.courier.requests.CreateCourier;
import ru.yandex.practicum.projects_4.model.courier.succcesResponses.SuccessCreateCourier;
import ru.yandex.practicum.projects_4.model.courier.succcesResponses.SuccessLoginCourier;
import ru.yandex.practicum.projects_4.model.courier.unSucccesResponses.UnSuccessCreateOrLogin;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest extends CourierGenerator {

    @Before
    public void installSpecification() {
        Specifications.requestSpec();
    }

    @Test
    public void successCreateCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        SuccessCreateCourier newCourierResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().as(SuccessCreateCourier.class);

        boolean actualOk = newCourierResponse.getOk();
        assertTrue(actualOk);

        SuccessLoginCourier loginCourierResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().as(SuccessLoginCourier.class);

        given()
                .when()
                .delete("/api/v1/courier/" + loginCourierResponse.getId());
    }

    @Test
    public void createDuplicateCourierTest() {
        CreateCourier newRandomCourier = buildNewRandomCourier();

        given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier");

        UnSuccessCreateOrLogin duplicateCourierResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(409)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Этот логин уже используется";
        String actualMessage = duplicateCourierResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);

        SuccessLoginCourier loginCourierResponse = given()
                .body(newRandomCourier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract().as(SuccessLoginCourier.class);

        given()
                .when()
                .delete("/api/v1/courier/" + loginCourierResponse.getId());
    }

    @Test
    public void createNoLoginCourierTest() {
        CreateCourier newNoLoginCourier = buildNewNoLoginRandomCourier();

        UnSuccessCreateOrLogin noLoginCourierResponse = given()
                .body(newNoLoginCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = noLoginCourierResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void createNoPasswordCourierTest() {
        CreateCourier newNoPasswordCourier = buildNewNoPasswordRandomCourier();

        UnSuccessCreateOrLogin noPasswordCourierResponse = given()
                .body(newNoPasswordCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400)
                .extract().as(UnSuccessCreateOrLogin.class);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = noPasswordCourierResponse.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}