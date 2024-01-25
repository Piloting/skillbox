package ru.pilot.skillbox.socnet.users.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.controller.dto.UserDto;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.entity.Gender;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.utbot.runtime.utils.java.UtUtils.createInstance;

public final class DtoMapperTest {
    
    @Test
    @DisplayName("entityToUser: UserEntityGetId -> return userDto")
    public void testEntityToUser_UserDtoSetHardSkills() throws Exception {
        Long id = 0L;
        UserEntity entity = new UserEntity(id);
        String email = "";
        entity.setEmail(email);
        entity.setPhone(email);
        UserState userState = UserState.ACTIVE;
        entity.setUserState(userState);
        LocalDateTime createDate = ((LocalDateTime) createInstance("java.time.LocalDateTime"));
        entity.setCreateDate(createDate);
        entity.setSurname(email);
        entity.setFirstname(email);
        entity.setPatronymic(email);
        Gender gender = Gender.MALE;
        entity.setGender(gender);
        entity.setBirthDate(createDate);
        entity.setCity(email);
        entity.setAvatarLink(email);
        entity.setInfo(email);
        entity.setHardSkills(email);

        UserDto actual = DtoMapper.entityToUser(entity);

        UserDto expected = new UserDto();
        expected.setId(id);
        expected.setEmail(email);
        expected.setPhone(email);
        expected.setUserState(userState);
        expected.setCreateDate(createDate);
        expected.setSurname(email);
        expected.setFirstname(email);
        expected.setPatronymic(email);
        expected.setGender(gender);
        expected.setBirthDate(createDate);
        expected.setCity(email);
        expected.setAvatarLink(email);
        expected.setInfo(email);
        expected.setHardSkills(email);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("entityToUserShort: UserEntityGetId -> return dto")
    public void testEntityToUserShort_UserShortDtoSetAvatarLink() throws Exception {
        Long id = 0L;
        UserEntity entity = new UserEntity(id);
        String surname = "";
        entity.setSurname(surname);
        entity.setFirstname(surname);
        entity.setPatronymic(surname);
        Gender gender = Gender.MALE;
        entity.setGender(gender);
        LocalDateTime birthDate = ((LocalDateTime) createInstance("java.time.LocalDateTime"));
        entity.setBirthDate(birthDate);
        entity.setCity(surname);
        entity.setAvatarLink(surname);

        UserShortDto actual = DtoMapper.entityToUserShort(entity);

        UserShortDto expected = new UserShortDto();
        expected.setId(id);
        expected.setSurname(surname);
        expected.setFirstname(surname);
        expected.setPatronymic(surname);
        expected.setGender(gender);
        expected.setBirthDate(birthDate);
        expected.setCity(surname);
        expected.setAvatarLink(surname);

        assertEquals(expected, actual);
    }
   
    @Test
    @DisplayName("registrationToEntity: arg_0 = RegistrationInfo()")
    public void testRegistrationToEntity() throws Exception {
        RegistrationInfo registrationInfo = new RegistrationInfo();

        UserEntity actual = DtoMapper.registrationToEntity(registrationInfo);

        UserEntity expected = new UserEntity(null);
        UserState userState = UserState.ACTIVE;
        expected.setUserState(userState);

        Long actualId = actual.getId();
        assertNull(actualId);

        String actualEmail = actual.getEmail();
        assertNull(actualEmail);

        String actualPhone = actual.getPhone();
        assertNull(actualPhone);

        UserState expectedUserState = expected.getUserState();
        UserState actualUserState = actual.getUserState();
        assertEquals(expectedUserState, actualUserState);

        String actualSurname = actual.getSurname();
        assertNull(actualSurname);

        String actualFirstname = actual.getFirstname();
        assertNull(actualFirstname);

        String actualPatronymic = actual.getPatronymic();
        assertNull(actualPatronymic);

        Gender actualGender = actual.getGender();
        assertNull(actualGender);

        LocalDateTime actualBirthDate = actual.getBirthDate();
        assertNull(actualBirthDate);

        String actualCity = actual.getCity();
        assertNull(actualCity);

        String actualAvatarLink = actual.getAvatarLink();
        assertNull(actualAvatarLink);

        String actualInfo = actual.getInfo();
        assertNull(actualInfo);

        String actualHardSkills = actual.getHardSkills();
        assertNull(actualHardSkills);
    }

    @Test
    @DisplayName("registrationToEntity: arg_0 = RegistrationInfo()")
    public void testRegistrationToEntity1() throws Exception {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setInfo("10");
        registrationInfo.setEmail("");
        registrationInfo.setAvatarLink("");
        registrationInfo.setPhone("XZ");
        registrationInfo.setSurname("-3");
        registrationInfo.setFirstname("XZ");
        registrationInfo.setPatronymic("abc");
        Gender gender = Gender.MALE;
        registrationInfo.setGender(gender);
        registrationInfo.setBirthDate(null);
        registrationInfo.setHardSkills("-3");

        UserEntity actual = DtoMapper.registrationToEntity(registrationInfo);

        UserEntity expected = new UserEntity(null);
        String email = "";
        expected.setEmail(email);
        String phone = "XZ";
        expected.setPhone(phone);
        UserState userState = UserState.ACTIVE;
        expected.setUserState(userState);
        String surname = "-3";
        expected.setSurname(surname);
        expected.setFirstname(phone);
        String patronymic = "abc";
        expected.setPatronymic(patronymic);
        expected.setGender(gender);
        expected.setAvatarLink(email);
        String info = "10";
        expected.setInfo(info);
        expected.setHardSkills(surname);

        Long actualId = actual.getId();
        assertNull(actualId);

        String expectedEmail = expected.getEmail();
        String actualEmail = actual.getEmail();
        assertEquals(expectedEmail, actualEmail);

        String expectedPhone = expected.getPhone();
        String actualPhone = actual.getPhone();
        assertEquals(expectedPhone, actualPhone);

        UserState expectedUserState = expected.getUserState();
        UserState actualUserState = actual.getUserState();
        assertEquals(expectedUserState, actualUserState);

        String expectedSurname = expected.getSurname();
        String actualSurname = actual.getSurname();
        assertEquals(expectedSurname, actualSurname);

        String expectedFirstname = expected.getFirstname();
        String actualFirstname = actual.getFirstname();
        assertEquals(expectedFirstname, actualFirstname);

        String expectedPatronymic = expected.getPatronymic();
        String actualPatronymic = actual.getPatronymic();
        assertEquals(expectedPatronymic, actualPatronymic);

        Gender expectedGender = expected.getGender();
        Gender actualGender = actual.getGender();
        assertEquals(expectedGender, actualGender);

        LocalDateTime actualBirthDate = actual.getBirthDate();
        assertNull(actualBirthDate);

        String actualCity = actual.getCity();
        assertNull(actualCity);

        String expectedAvatarLink = expected.getAvatarLink();
        String actualAvatarLink = actual.getAvatarLink();
        assertEquals(expectedAvatarLink, actualAvatarLink);

        String expectedInfo = expected.getInfo();
        String actualInfo = actual.getInfo();
        assertEquals(expectedInfo, actualInfo);

        String expectedHardSkills = expected.getHardSkills();
        String actualHardSkills = actual.getHardSkills();
        assertEquals(expectedHardSkills, actualHardSkills);

    }
}
