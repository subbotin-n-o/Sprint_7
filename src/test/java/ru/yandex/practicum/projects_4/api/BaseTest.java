package ru.yandex.practicum.projects_4.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import ru.yandex.practicum.projects_4.model.Courier;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @BeforeClass
    public static void prepare() {
        // загрузить в системные переменные "base.uri" из "config.properties"
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String baseUri = System.getProperty("base.uri");
        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException("В файле \"config.properties\" отсутствует значение \"base.uri\"");
        }

        // подготовить глобальные преднастройки для запросов
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.filters(new ResponseLoggingFilter());
    }

    protected Courier buildNewCourier() {
        Courier courier = new Courier();
        String login = "Courier_" + UUID.randomUUID();
        String password = String.valueOf(new Random().nextInt(1000));
        String firstName = "Courier_" + UUID.randomUUID();

        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

//    protected Courier createCourier(Courier courier) {
//        // отправить HTTP запрос для создания курьера
//
//        return given()
//                .body(courier)
//                .when()
//                .post("/api/v1/courier")
//                .then()
//                .assertThat()
//                .statusCode(201)
//                .extract()
//                .body()
//                .as(Courier.class);
//    }

}