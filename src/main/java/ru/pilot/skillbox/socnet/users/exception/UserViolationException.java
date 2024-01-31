package ru.pilot.skillbox.socnet.users.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.pilot.skillbox.socnet.users.exception.dto.Violation;

@Getter
@ToString
@RequiredArgsConstructor
public class UserViolationException extends RuntimeException{
    private final List<Violation> violations;

    public UserViolationException(Violation violation){
        violations = Collections.singletonList(violation);
    }

    @Override
    public String getMessage() {
        return violations.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
