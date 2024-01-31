package ru.pilot.skillbox.socnet.users.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.pilot.skillbox.socnet.users.exception.dto.ValidationErrorResponse;
import ru.pilot.skillbox.socnet.users.exception.dto.Violation;

/**
 * Обработка ошибок валидации из невнятного текста в красивый json
 */
@Slf4j
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
        toLog(violations);
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        toLog(violations);
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserViolationException.class)
    public ValidationErrorResponse onUserViolationException(UserViolationException e) {
        List<Violation> violations = e.getViolations();
        toLog(violations);
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    public ValidationErrorResponse onPropertyReferenceException(PropertyReferenceException e) {
        List<Violation> violations = Collections.singletonList(new Violation(e.getPropertyName(), e.getMessage()));
        toLog(violations);
        return new ValidationErrorResponse(violations);
    }

    private void toLog(List<Violation> violations) {
        String errorsForLog = violations.stream().map(Object::toString).collect(Collectors.joining(", "));
        log.error("Validate Errors: {}", errorsForLog);
    }
}
