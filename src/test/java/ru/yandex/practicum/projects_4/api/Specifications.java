package ru.yandex.practicum.projects_4.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import java.io.IOException;

public class Specifications {

    public static void requestSpec() {
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


//    public static RequestSpecification requestSpec(String baseUrl) {
//        return new RequestSpecBuilder()
//                .setBaseUri(baseUrl)
//                .setAccept(ContentType.JSON)
//                .setContentType(ContentType.JSON)
//                .log(LogDetail.ALL)
//                .build();
//    }

//    public static ResponseSpecification responseSpecOk200() {
//        return new ResponseSpecBuilder()
//                .expectStatusCode(200)
//                .build();
//    }
//
//    public static ResponseSpecification responseSpecOk201() {
//        return new ResponseSpecBuilder()
//                .expectStatusCode(201)
//                .build();
//    }
//
//    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
//        RestAssured.requestSpecification = request;
//        RestAssured.responseSpecification = response;
//    }
}