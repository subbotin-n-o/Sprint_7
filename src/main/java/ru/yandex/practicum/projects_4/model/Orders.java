package ru.yandex.practicum.projects_4.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    private int id;
    private String message;
    private int track;
    private int courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private List<String> color;
    private String comment;
    private  String createdAt;
    private String updatedAt;
    private int status;

}