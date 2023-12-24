package ru.pilot.skillbox.socnet.users.mapper;

import java.time.LocalDateTime;

import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.controller.dto.UserDto;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;

/** 
 * Конвертация дто
 * Подключить Mapstruct, если будет много
 */
public class DtoMapper {
    public static UserEntity registrationToEntity(RegistrationInfo registrationInfo){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registrationInfo.getEmail());
        userEntity.setPhone(registrationInfo.getPhone());
        userEntity.setUserState(UserState.ACTIVE);
        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setSurname(registrationInfo.getSurname());
        userEntity.setFirstname(registrationInfo.getFirstname());
        userEntity.setPatronymic(registrationInfo.getPatronymic());
        userEntity.setGender(registrationInfo.getGender());
        userEntity.setBirthDate(registrationInfo.getBirthDate());
        userEntity.setCity(registrationInfo.getCity());
        userEntity.setAvatarLink(registrationInfo.getAvatarLink());
        userEntity.setInfo(registrationInfo.getInfo());
        userEntity.setHardSkills(registrationInfo.getHardSkills());
        return userEntity;
    }
    
    public static UserDto entityToUser(UserEntity entity){
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setPhone(entity.getPhone());
        userDto.setUserState(entity.getUserState());
        userDto.setCreateDate(entity.getCreateDate());
        userDto.setSurname(entity.getSurname());
        userDto.setFirstname(entity.getFirstname());
        userDto.setPatronymic(entity.getPatronymic());
        userDto.setGender(entity.getGender());
        userDto.setBirthDate(entity.getBirthDate());
        userDto.setCity(entity.getCity());
        userDto.setAvatarLink(entity.getAvatarLink());
        userDto.setInfo(entity.getInfo());
        userDto.setHardSkills(entity.getHardSkills());
        return userDto;
    }
    public static UserShortDto entityToUserShort(UserEntity entity){
        UserShortDto dto = new UserShortDto();
        dto.setId(entity.getId());
        dto.setSurname(entity.getSurname());
        dto.setFirstname(entity.getFirstname());
        dto.setPatronymic(entity.getPatronymic());
        dto.setGender(entity.getGender());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCity(entity.getCity());
        dto.setAvatarLink(entity.getAvatarLink());
        return dto;
    }
}
