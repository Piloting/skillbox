package ru.pilot.skillbox.socnet.users.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;
import ru.pilot.skillbox.socnet.users.entity.Gender;

@Data
public class UserShortDto {
    private Long id;
    private String surname;
    private String firstname;
    private String patronymic;
    private Gender gender;
    private LocalDateTime birthDate;
    private String city;
    private String avatarLink;
}
