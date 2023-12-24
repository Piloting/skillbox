package ru.pilot.skillbox.socnet.users.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pilot.skillbox.socnet.users.aop.Loggable;
import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.controller.dto.UserDto;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.service.SubscriptionService;
import ru.pilot.skillbox.socnet.users.service.UserService;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    
    /** Проверка наличия пользователя с указанным номером телефона */
    @Loggable
    @GetMapping("user/checkExistsPhone")
    @Operation(summary = "Проверка телефона", description = "Проверка наличия пользователя с указанным номером телефона")
    public boolean checkExistsPhone(
            @RequestParam("phone") @NotBlank
            @Parameter(description = "Номер телефона")
            String phoneNumber
    ){
        return userService.checkExistsPhone(phoneNumber);
    }

    /** Проверка наличия пользователя с указанной эл. почтой */
    @Loggable
    @GetMapping("user/checkExistsEmail")
    @Operation(summary = "Проверка почты", description = "Проверка наличия пользователя с указанной эл. почтой")
    public boolean checkExistsEmail(
            @RequestParam("email") @Email 
            @Parameter(description = "Эл. почта")
            String email
    ){
        return userService.checkExistsEmail(email);
    }
    
    /** Регистрация пользователя */
    @Loggable
    @PostMapping("user")
    @Operation(summary = "Регистрация пользователя", description = "Добавление нового пользователя")
    public Long addUser(
            @RequestBody @NotNull @Valid
            @Parameter(description = "Информация о пользователе") 
            RegistrationInfo registrationInfo){
        return userService.addUser(registrationInfo);
    }
    
    /** Просмотр пользователя */
    @Loggable
    @Cacheable("users")
    @GetMapping("user/{id}")
    @Operation(summary = "Просмотр пользователя", description = "Просмотр информации о пользователе")
    public Optional<UserDto> getUser(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID пользователя")
            Long userId
    ){
        return userService.getUser(userId);
    }
    
    /** Редактирование пользователя */
    @Loggable
    @CacheEvict(value = "users", key = "#userId")
    @PutMapping("user/{id}")
    @Operation(summary = "Редактирование пользователя", description = "Редактирование пользователя")
    public void updateUser(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID пользователя")
            Long userId, 
            @RequestBody @Valid
            @Parameter(description = "Информация о пользователе")
            RegistrationInfo userDto
    ){
        userService.updateUser(userId, userDto);
    }
    
    /** Удаление пользователя */
    @Loggable
    @CacheEvict(value = "users", key = "#userId")
    @DeleteMapping("user/{id}")
    @Operation(summary = "Удаление пользователя", description = "Перевод пользователя в состояние Удален")
    public void deleteUser(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID пользователя")
            Long userId
    ){
        userService.deleteUser(userId);
    }
    
    /** Просмотр списка подписчиков пользователя */
    @Loggable
    @GetMapping("user/{id}/subscribers")
    @Operation(summary = "Получение подписчиков", description = "Просмотр списка подписчиков пользователя")
    public List<UserShortDto> getSubscribers(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID пользователя")
            Long subscriberUserId
    ){
        return subscriptionService.getSubscribers(subscriberUserId);
    }
    
    /** Просмотр списка подписок пользователя */
    @Loggable
    @GetMapping("user/{id}/subscription")
    @Operation(summary = "Получение подписок", description = "Просмотр списка подписок пользователя")
    public List<UserShortDto> getSubscription(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID пользователя")
            Long ownerUserId
    ){
        return subscriptionService.getSubscription(ownerUserId);
    }
    
    /** Добавление новой подписки */
    @Loggable
    @PostMapping("user/{id}/subscribe/{subs_id}")
    @Operation(summary = "Добавление новой подписки", description = "Добавление новой подписки")
    public void subscribe(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID издателя")
            Long ownerUserId, 
            @PathVariable("subs_id") @Min(0)
            @Parameter(description = "ID подписчика")
            Long subscriberUserId
    ){
        subscriptionService.addSubscription(ownerUserId, subscriberUserId);
    }
    
    /** Удаление подписки */
    @Loggable
    @PostMapping("user/{id}/unsubscribe/{subs_id}")
    @Operation(summary = "Удаление подписки", description = "Удаление подписки")
    public void unsubscribe(
            @PathVariable("id") @Min(0)
            @Parameter(description = "ID издателя")
            Long ownerUserId, 
            @PathVariable("subs_id") @Min(0)
            @Parameter(description = "ID подписчика")
            Long subscriberUserId
    ){
        subscriptionService.deleteSubscription(ownerUserId, subscriberUserId);
    }

    /** Просмотр пользователей */
    @Loggable
    @GetMapping("users")
    @Operation(summary = "Просмотр пользователей", description = "Просмотр пользователей")
    public List<UserShortDto> browseUser(
            @RequestParam(value = "surname", required = false)
            @Parameter(description = "Фамилия")
            String surname,
            @RequestParam(value = "city", required = false)
            @Parameter(description = "Город")
            String city,
            @Parameter(description = "Пагинация")
            @NotNull Pageable pageable
    ) {
        Page<UserShortDto> resultPage = userService.getAll(surname, city, pageable);
        return resultPage.getContent();
    }
}
