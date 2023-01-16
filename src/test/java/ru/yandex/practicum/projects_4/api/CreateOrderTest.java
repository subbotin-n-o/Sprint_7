package ru.yandex.practicum.projects_4.api;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.projects_4.model.order.Order;
import ru.yandex.practicum.projects_4.model.order.OrderClient;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final Order order;
    private final OrderClient orderClient;

    public CreateOrderTest(Order order, OrderClient orderClient) {
        this.order = order;
        this.orderClient = orderClient;
    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][]{
                {OrderGenerator.getRandomOrderNoColors(), new OrderClient()},
                {OrderGenerator.getRandomOrderSingleColors(), new OrderClient()},
                {OrderGenerator.getRandomOrderDoubleColors(), new OrderClient()},
        };

    }

    @Test
    public void succesCreateOrder() {

        ValidatableResponse response = orderClient.create(order);

        int actualStatusCode = response.extract().statusCode();
        int actualTrack = response.extract().path("track");

        assertEquals(SC_CREATED, actualStatusCode);
        assertNotNull(actualTrack);

    }

}