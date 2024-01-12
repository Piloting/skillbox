package ru.pilot.skillbox.socnet.users.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.controller.dto.UserDto;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;
import ru.pilot.skillbox.socnet.users.exception.UserViolationException;
import ru.pilot.skillbox.socnet.users.exception.dto.Violation;
import ru.pilot.skillbox.socnet.users.mapper.DtoMapper;
import ru.pilot.skillbox.socnet.users.repository.UserRepository;

/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /** Проверка наличия пользователя с указанным номером телефона */
    public boolean checkExistsPhone(String phoneNumber){
        return userRepository.existsByPhoneAndUserState(phoneNumber, UserState.ACTIVE);
    }

    /** Проверка наличия пользователя с указанной эл. почтой */
    public boolean checkExistsEmail(String email){
        return userRepository.existsByEmailAndUserState(email, UserState.ACTIVE);
    }

    /** Добавление нового пользователя */
    @Transactional
    public Long addUser(RegistrationInfo registrationInfo) {
        insertValidate(registrationInfo);
        UserEntity userEntity = DtoMapper.registrationToEntity(registrationInfo);
        userRepository.save(userEntity);
        return userEntity.getId();
    }

    /** Получение пользователя по ID */
    public Optional<UserDto> getUser(Long userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        return userOpt.map(DtoMapper::entityToUser);
    }

    /** Изменение пользователя */
    @Transactional
    public void updateUser(Long userId, RegistrationInfo registrationInfo) {
        validateUserState(userId);
        validateUniqueEmail(userId, registrationInfo.getEmail());
        validateUniquePhone(userId, registrationInfo.getPhone());
        
        UserEntity newUserEntity = DtoMapper.registrationToEntity(registrationInfo);
        newUserEntity.setId(userId);
        userRepository.save(newUserEntity);
    }

    /** Удаление пользователя */
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = validateUserState(userId);
        user.setUserState(UserState.DELETE);
        userRepository.save(user);
    }
    
    private void validateUniqueEmail(Long userId, String email) {
        boolean emailBusy = userRepository.existsByEmailAndUserStateAndIdNot(
                email, UserState.ACTIVE, userId);
        if (emailBusy){
            throw new UserViolationException(
                    new Violation("email", "Пользователь с такой эл. почтой уже зарегистрирован"));
        }
    }

    private void validateUniquePhone(Long userId, String phone) {
        boolean phoneBusy = userRepository.existsByPhoneAndUserStateAndIdNot(
                phone, UserState.ACTIVE, userId);
        if (phoneBusy){
            throw new UserViolationException(
                    new Violation("phone", "Пользователь с таким номером телефона уже зарегистрирован"));
        }
    }

    private void insertValidate(RegistrationInfo registrationInfo) {
        if (checkExistsEmail(registrationInfo.getEmail())){
            throw new UserViolationException(
                    new Violation("email", "Пользователь с такой эл. почтой уже зарегистрирован"));
        }
        if (checkExistsPhone(registrationInfo.getPhone())){
            throw new UserViolationException(
                    new Violation("phone", "Пользователь с таким номером телефона уже зарегистрирован"));
        }
    }

    public UserEntity validateUserState(Long userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        userOpt.orElseThrow(() -> new UserViolationException(new Violation("userId", "Пользователь не найден")));

        UserEntity userEntity = userOpt.get();
        if (userEntity.getUserState() == UserState.DELETE){
            throw new UserViolationException(new Violation("userId", "Пользователь удален"));
        }

        return userEntity;
    }

    public Page<UserShortDto> getAll(String surname, String city, Pageable pageable) {
        Page<UserEntity> page;
        if (StringUtils.isNotBlank(surname) && StringUtils.isNotBlank(city)){
            page = userRepository.findAllBySurnameAndCity(surname, city, pageable);
        } else if (StringUtils.isNotBlank(surname)) {
            page = userRepository.findAllBySurname(surname, pageable);
        } else if (StringUtils.isNotBlank(city)) {
            page = userRepository.findAllByCity(city, pageable);
        } else {
            page = userRepository.findAll(pageable);
        }
        return page.map(DtoMapper::entityToUserShort);
    }
}
