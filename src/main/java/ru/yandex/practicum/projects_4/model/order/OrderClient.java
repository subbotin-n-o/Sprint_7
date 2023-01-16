package ru.yandex.practicum.projects_4.model.order;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.projects_4.model.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH_CREATE = "api/v1/orders";
    private static final String PATH_CANCEL = "api/v1/orders/cancel";


    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_CREATE)
                .then();
    }

}