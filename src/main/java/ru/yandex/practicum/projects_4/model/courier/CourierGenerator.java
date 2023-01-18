package ru.yandex.practicum.projects_4.model.courier;

import java.util.UUID;

public class CourierGenerator {

    public static Courier getRandomCourier() {
        Courier courier = new Courier();
        String login = "login_" + UUID.randomUUID();
        String password = "password_" + UUID.randomUUID();
        String firstName = "login_" + UUID.randomUUID();

        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

}