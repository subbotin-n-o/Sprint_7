package ru.yandex.practicum.projects_4.model.courier;

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