package ru.yandex.practicum.projects_4.model.courier.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourier {
    private String login;
    private String password;
    private String firstName;
}