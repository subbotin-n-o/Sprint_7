package ru.yandex.practicum.projects_4.api;

import ru.yandex.practicum.projects_4.model.order.Order;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderGenerator {

    private static final int PHONE_NUMBER_LENGTH = 10;

    private static final String firstName = "firstName_" + UUID.randomUUID();
    private static final String lastName = "lastName_" + UUID.randomUUID();
    private static final String address = "address_" + UUID.randomUUID();
    private static final Integer metroStation = new Random().nextInt(9);
    private static final String phone = "+7 " + getRandomPhone();
    private static final Integer rentTime = new Random().nextInt(9);
    private static final String deliveryDate = getDeliveryDate();
    private static final String comment = "comment_" + UUID.randomUUID();
    private static final List<String> doubleColorsList = Arrays.asList("BLACK", "GREY");
    private static final List<String> singleColorsList = List.of("BLACK", "");
    private static final List<String> noColorsList = Arrays.asList("", "");

    public static Order getRandomOrderDoubleColors() {
        Order order = getRandomOrderNoColors();
        order.setColor(doubleColorsList);
        return order;
    }

    public static Order getRandomOrderSingleColors() {
        Order order = getRandomOrderNoColors();
        order.setColor(singleColorsList);
        return order;
    }

    public static Order getRandomOrderNoColors() {
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryDate(deliveryDate);
        order.setComment(comment);
        order.setColor(noColorsList);
        return order;
    }

    private static String getRandomPhone() {
        String s = "123456789";
        StringBuilder phoneNumber = new StringBuilder();

        for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
            phoneNumber.append(s.charAt(new Random().nextInt(s.length())));
        }
        return phoneNumber.toString();
    }

    private static String getDeliveryDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

}