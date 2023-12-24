package ru.pilot.skillbox.socnet.users.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;
import ru.pilot.skillbox.socnet.users.entity.Gender;
import ru.pilot.skillbox.socnet.users.entity.UserState;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String phone;
    private UserState userState;
    private LocalDateTime createDate;
    private String surname;
    private String firstname;
    private String patronymic;
    private Gender gender;
    private LocalDateTime birthDate;
    private String city;
    private String avatarLink;
    private String info;
    private String hardSkills;
}
