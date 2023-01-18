package ru.yandex.practicum.projects_4.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.Client;
import ru.yandex.practicum.projects_4.model.order.OrderClient;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class ListOrdersTest extends Client {

    private OrderClient orderClient;

    @Test
    @DisplayName("Check list Orders")
    @Description("Expected response: StatusCode 200")
    public void getListOrders() {

        orderClient = new OrderClient();

        ValidatableResponse response = orderClient.getListOrders();

        int actualStatusCode = response.extract().statusCode();
        List<String> actualOrders = response.extract().path("orders");

        assertEquals(SC_OK, actualStatusCode);
        assertNotNull(actualOrders);

    }
}