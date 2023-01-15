package ru.yandex.practicum.projects_4.api;

import ru.yandex.practicum.projects_4.model.courier.requests.CreateCourier;

import java.util.Random;
import java.util.UUID;

public class CourierGenerator {

    public CreateCourier buildNewCourier(String login, String password, String firstName) {
        CreateCourier courier = new CreateCourier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    public CreateCourier buildNewRandomCourier() {
        CreateCourier courier = new CreateCourier();
        String login = "Courier_" + UUID.randomUUID();
        String password = String.valueOf(new Random().nextInt(10000));
        String firstName = "Courier_" + UUID.randomUUID();

        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    public CreateCourier buildNewNoLoginRandomCourier() {
        CreateCourier courier = new CreateCourier();
        String password = String.valueOf(new Random().nextInt(10000));
        String firstName = "Courier_" + UUID.randomUUID();

        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    public CreateCourier buildNewNoPasswordRandomCourier() {
        CreateCourier courier = new CreateCourier();
        String login = "Courier_" + UUID.randomUUID();
        String firstName = "Courier_" + UUID.randomUUID();

        courier.setLogin(login);
        courier.setFirstName(firstName);
        return courier;
    }

}