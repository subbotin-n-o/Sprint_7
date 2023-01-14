package ru.yandex.practicum.projects_4.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.Courier;


import static io.restassured.RestAssured.given;

public class CreateCourierTest extends BaseTest {

    @Test
    public void createCourierReturnsSuccess() {
        Courier randomCourier = buildNewCourier();

        given()
                .body(randomCourier)
                .post("/api/v1/courier")
                .then()
                .extract()
                .as(Courier.class);

        String json = given()
                .body(randomCourier)
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .asString();

        Courier idCourier = new Gson().fromJson(json, Courier.class);

        given()
                .when()
                .delete("/api/v1/courier/" + idCourier.getId())
                .then()
                .statusCode(200);
    }

}