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

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();

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
    @DisplayName("Success create Courier")
    @Description("Expected response: StatusCode 201")
    public void SuccesCreateCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int actualStatusCode = response.extract().statusCode();
        boolean actualOk = response.extract().path("ok");

        assertEquals(SC_CREATED, actualStatusCode);
        assertTrue(actualOk);

    }

    @Test
    @DisplayName("Check create duplicate Courier")
    @Description("Expected response: StatusCode 409")
    public void createDuplicateCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse duplicateResponse = courierClient.create(courier);

        String expectedMessage = "Этот логин уже используется";
        String actualMessage = duplicateResponse.extract().path("message");

        int actualStatusCode = duplicateResponse.extract().statusCode();

        assertEquals(SC_CONFLICT, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

    }

    @Test
    @DisplayName("Check create Courier no login")
    @Description("Expected response: StatusCode 400")
    public void createCourierNoLoginTest() {
        courier.setLogin("");

        ValidatableResponse response = courierClient.create(courier);

        String expectedMessage = "Недостаточно данных для создания учетной записи";

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Check create Courier no password")
    @Description("Expected response: StatusCode 400")
    public void createCourierNoPasswordTest() {
        courier.setPassword(null);

        ValidatableResponse response = courierClient.create(courier);

        String expectedMessage = "Недостаточно данных для создания учетной записи";

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);

    }

}