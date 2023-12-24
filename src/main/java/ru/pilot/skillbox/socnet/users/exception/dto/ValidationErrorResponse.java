package ru.pilot.skillbox.socnet.users.exception.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final List<Violation> violations;
}

