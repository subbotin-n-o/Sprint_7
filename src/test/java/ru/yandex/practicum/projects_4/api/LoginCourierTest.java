package ru.yandex.practicum.projects_4.api;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.courier.Courier;
import ru.yandex.practicum.projects_4.model.courier.CourierClient;
import ru.yandex.practicum.projects_4.model.courier.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);

    }

    @After
    public void cleanUp() {
        if (id != 0) {
            ValidatableResponse deleteResponse = courierClient.delete(id);

            assertEquals(SC_OK, deleteResponse.extract().statusCode());
            assertTrue(deleteResponse.extract().path("ok"));

        }
    }

    @Test
    public void succesLoginCourierTest() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int actualStatusCode = loginResponse.extract().statusCode();
        int actualId = loginResponse.extract().path("id");

        assertEquals(SC_OK, actualStatusCode);
        assertNotNull(actualId);

    }

    @Test
    public void loginCourierNoLoginTest() {
        courier.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        String expectedMessage = "Недостаточно данных для входа";

        int actualStatusCode = loginResponse.extract().statusCode();
        String actualMessage = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void loginCourierNoPasswordTest() {
        courier.setPassword("");
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        String expectedMessage = "Недостаточно данных для входа";

        int actualStatusCode = loginResponse.extract().statusCode();
        String actualMessage = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void loginCourierNotValidDataTest() {
        courier = CourierGenerator.getRandomCourier();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        String expectedMessage = "Учетная запись не найдена";

        int actualStatusCode = loginResponse.extract().statusCode();
        String actualMessage = loginResponse.extract().path("message");

        assertEquals(SC_NOT_FOUND, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

}