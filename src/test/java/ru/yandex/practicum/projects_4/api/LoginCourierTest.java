package ru.yandex.practicum.projects_4.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.projects_4.model.courier.Courier;
import ru.yandex.practicum.projects_4.model.courier.CourierClient;
import ru.yandex.practicum.projects_4.model.courier.CourierCredentials;
import ru.yandex.practicum.projects_4.model.courier.CourierGenerator;

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

        courierClient.createCourier(courier);

    }

    @After
    public void cleanUp() {
        if (id != 0) {
            ValidatableResponse deleteResponse = courierClient.deleteCourier(id);

            assertEquals(SC_OK, deleteResponse.extract().statusCode());
            assertTrue(deleteResponse.extract().path("ok"));

        }
    }

    @Test
    @DisplayName("Succes login")
    @Description("Expected response: StatusCode 200")
    public void succesLoginTest() {
        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.from(courier));

        int actualStatusCode = response.extract().statusCode();
        int actualId = response.extract().path("id");

        assertEquals(SC_OK, actualStatusCode);
        assertNotNull(actualId);

    }

    @Test
    @DisplayName("Check login no login")
    @Description("Expected response: StatusCode 400")
    public void loginNoLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.from(courier));

        String expectedMessage = "Недостаточно данных для входа";

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Check login no password")
    @Description("Expected response: StatusCode 400")
    public void loginNoPasswordTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.from(courier));

        String expectedMessage = "Недостаточно данных для входа";

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Check login invalid data")
    @Description("Expected response: StatusCode 404")
    public void loginInvalidDataTest() {
        courier = CourierGenerator.getRandomCourier();
        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.from(courier));

        String expectedMessage = "Учетная запись не найдена";

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals(SC_NOT_FOUND, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

}