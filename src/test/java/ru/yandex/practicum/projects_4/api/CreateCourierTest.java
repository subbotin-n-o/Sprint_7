package ru.yandex.practicum.projects_4.api;

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
    public void successCreateCourierTest() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int actualStatusCode = response.extract().statusCode();
        boolean actualOk = response.extract().path("ok");

        assertEquals(SC_CREATED, actualStatusCode);
        assertTrue(actualOk);

    }

    @Test
    public void createDuplicateCourierTest() {  //баг в response
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