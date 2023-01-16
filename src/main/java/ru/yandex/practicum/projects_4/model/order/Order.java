package ru.yandex.practicum.projects_4.model.order;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
}