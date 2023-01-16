package ru.yandex.practicum.projects_4.model.courier;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.projects_4.model.Client;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH_CREATE = "api/v1/courier";
    private static final String PATH_LOGIN = "api/v1/courier/login";
    private static final String PATH_DELETE = "api/v1/courier/";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_CREATE)
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    public ValidatableResponse delete(Integer id) {
        return given()
                .spec(getSpec())
                .delete(PATH_DELETE + id)
                .then();
    }
}