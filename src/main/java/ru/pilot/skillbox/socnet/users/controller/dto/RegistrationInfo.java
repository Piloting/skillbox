package ru.pilot.skillbox.socnet.users.controller.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.pilot.skillbox.socnet.users.entity.Gender;

@Data
public class RegistrationInfo {
    @Email
    @Size(max = 255)
    private String email;
    
    @NotBlank
    @Size(max = 50)
    private String phone;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Size(max = 50)
    private String firstname;

    @Size(max = 50)
    private String patronymic;
    
    private Gender gender;
    
    private LocalDateTime birthDate;
    
    @NotBlank
    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String avatarLink;

    @Size(max = 2000)
    private String info;
    
    @Size(max = 2000)
    private String hardSkills;
}
