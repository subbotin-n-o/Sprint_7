package ru.yandex.practicum.projects_4.model.order;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.projects_4.model.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH = "api/v1/orders";


    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse getListOrders() {
        return given()
                .spec(getSpec())
                .get(PATH)
                .then();
    }

}