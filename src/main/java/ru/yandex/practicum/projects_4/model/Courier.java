package ru.yandex.practicum.projects_4.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    private String login;
    private String password;
    private String firstName;
    private String id;

    @Override
    public String toString() {
        return "Courier{" +
                "login" + login + '\'' +
                ",password ='" + password + '\'' +
                ",firstName ='" + firstName + '\'' +
                ",id ='" + id + '\'' +
                "}";
    }

}