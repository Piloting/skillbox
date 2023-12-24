package ru.pilot.skillbox.socnet.users.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
