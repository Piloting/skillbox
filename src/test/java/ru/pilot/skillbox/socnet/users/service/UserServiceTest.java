package ru.pilot.skillbox.socnet.users.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.pilot.skillbox.socnet.users.controller.dto.RegistrationInfo;
import ru.pilot.skillbox.socnet.users.entity.Gender;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;
import ru.pilot.skillbox.socnet.users.repository.UserRepository;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.utbot.runtime.utils.java.UtUtils.deepEquals;

public final class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    private AutoCloseable mockitoCloseable;
    
    @Test
    @DisplayName("addUser: registrationInfo = mock() -> return null")
    public void testAddUser() {
        (when(userRepositoryMock.existsByEmailAndUserState(any(), any()))).thenReturn(false);
        (when(userRepositoryMock.existsByPhoneAndUserState(any(), any()))).thenReturn(false);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(userRepositoryMock.save(any()))).thenReturn(userEntityMock);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("#$\\\"'");
        (when(registrationInfoMock.getPhone())).thenReturn("XZ");
        (when(registrationInfoMock.getSurname())).thenReturn("-3");
        (when(registrationInfoMock.getFirstname())).thenReturn("XZ");
        (when(registrationInfoMock.getPatronymic())).thenReturn("abc");
        Gender genderMock = mock(Gender.class);
        (when(registrationInfoMock.getGender())).thenReturn(genderMock);
        LocalDateTime localDateTimeMock = mock(LocalDateTime.class);
        (when(registrationInfoMock.getBirthDate())).thenReturn(localDateTimeMock);
        (when(registrationInfoMock.getCity())).thenReturn("abc");
        (when(registrationInfoMock.getAvatarLink())).thenReturn("#$\\\"'");
        (when(registrationInfoMock.getInfo())).thenReturn("abc");
        (when(registrationInfoMock.getHardSkills())).thenReturn("10");

        Long actual = userService.addUser(registrationInfoMock);

        assertNull(actual);
    }

    @Test
    @DisplayName("addUser: registrationInfo = mock() -> return null")
    public void testAddUser1() {
        (when(userRepositoryMock.existsByEmailAndUserState(any(), any()))).thenReturn(false);
        (when(userRepositoryMock.existsByPhoneAndUserState(any(), any()))).thenReturn(false);
        (when(userRepositoryMock.save(any()))).thenReturn(null);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn(((String) null));
        (when(registrationInfoMock.getPhone())).thenReturn(((String) null));
        (when(registrationInfoMock.getSurname())).thenReturn(((String) null));
        (when(registrationInfoMock.getFirstname())).thenReturn(((String) null));
        (when(registrationInfoMock.getPatronymic())).thenReturn(((String) null));
        Gender genderMock = mock(Gender.class);
        (when(registrationInfoMock.getGender())).thenReturn(genderMock);
        LocalDateTime localDateTimeMock = mock(LocalDateTime.class);
        (when(registrationInfoMock.getBirthDate())).thenReturn(localDateTimeMock);
        (when(registrationInfoMock.getCity())).thenReturn(((String) null));
        (when(registrationInfoMock.getAvatarLink())).thenReturn(((String) null));
        (when(registrationInfoMock.getInfo())).thenReturn(((String) null));
        (when(registrationInfoMock.getHardSkills())).thenReturn(((String) null));

        Long actual = userService.addUser(registrationInfoMock);

        assertNull(actual);
    }
    
    @Test
    @DisplayName("addUser: registrationInfo = mock() -> throw RuntimeException")
    public void testAddUserThrowsRE() {
        (when(userRepositoryMock.existsByEmailAndUserState(any(), any()))).thenReturn(true);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("-3");

        assertThrows(RuntimeException.class, () -> userService.addUser(registrationInfoMock));
    }
    
    @Test
    @DisplayName("addUser: registrationInfo = mock() -> throw RuntimeException")
    public void testAddUserThrowsRE1() {
        (when(userRepositoryMock.existsByEmailAndUserState(any(), any()))).thenReturn(false);
        (when(userRepositoryMock.existsByPhoneAndUserState(any(), any()))).thenReturn(true);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("abc");
        (when(registrationInfoMock.getPhone())).thenReturn("10");

        assertThrows(RuntimeException.class, () -> userService.addUser(registrationInfoMock));
    }
    
    @Test
    @DisplayName("checkExistsEmail: email = '\u000E' (mutated from '') -> return false")
    public void testCheckExistsEmailReturnsFalseWithNonEmptyString() {
        (when(userRepositoryMock.existsByEmailAndUserState(any(), any()))).thenReturn(false);

        boolean actual = userService.checkExistsEmail("\u000E");

        assertFalse(actual);
    }

    @Test
    @DisplayName("checkExistsPhone: phoneNumber = '\u000E' (mutated from '') -> return false")
    public void testCheckExistsPhoneReturnsFalseWithNonEmptyString() {
        (when(userRepositoryMock.existsByPhoneAndUserState(any(), any()))).thenReturn(false);

        boolean actual = userService.checkExistsPhone("\u000E");

        assertFalse(actual);
    }
    
    @Test
    @DisplayName("checkExistsPhone: phoneNumber = '\u000E' -> return false")
    public void testCheckExistsPhoneReturnsFalseWithNonEmptyString1() {
        (when(userRepositoryMock.existsByPhoneAndUserState(any(), any()))).thenReturn(false);

        boolean actual = userService.checkExistsPhone("\u000E");

        assertFalse(actual);
    }
    
    @Test
    @DisplayName("deleteUser: userId = 67108865 (mutated from positive)")
    public void testDeleteUser() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.ACTIVE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (((UserEntity) (doNothing()).when(userEntityMock1))).setUserState(any());
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);
        UserEntity userEntityMock2 = mock(UserEntity.class);
        (when(userRepositoryMock.save(any()))).thenReturn(userEntityMock2);

        userService.deleteUser(67108865L);
    }
    
    @Test
    @DisplayName("deleteUser: userId = -2049 (mutated from -1) -> throw RuntimeException")
    public void testDeleteUserThrowsRE() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.DELETE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(-2049L));
    }
    
    @Test
    @DisplayName("getUser: userId = -9 (mutated from -1)")
    public void testGetUser() {
        Optional optionalMock = mock(Optional.class);
        Optional optionalMock1 = mock(Optional.class);
        (when(optionalMock1.isPresent())).thenReturn(false);
        (when(optionalMock.map(any()))).thenReturn(optionalMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);

        Optional actual = userService.getUser(-9L);

        Optional expected = empty();

        assertTrue(deepEquals(expected, actual));
    }
    
    @Test
    @DisplayName("updateUser: userId = -32769 (mutated from -1), registrationInfo = mock()")
    public void testUpdateUser() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.ACTIVE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);
        (when(userRepositoryMock.existsByEmailAndUserStateAndIdNot(any(), any(), any()))).thenReturn(false);
        (when(userRepositoryMock.existsByPhoneAndUserStateAndIdNot(any(), any(), any()))).thenReturn(false);
        UserEntity userEntityMock2 = mock(UserEntity.class);
        (when(userRepositoryMock.save(any()))).thenReturn(userEntityMock2);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("#$\\\"'");
        (when(registrationInfoMock.getPhone())).thenReturn("#$\\\"'");
        (when(registrationInfoMock.getSurname())).thenReturn(((String) null));
        (when(registrationInfoMock.getFirstname())).thenReturn("XZ");
        (when(registrationInfoMock.getPatronymic())).thenReturn("-3");
        Gender gender = Gender.MALE;
        (when(registrationInfoMock.getGender())).thenReturn(gender);
        LocalDateTime localDateTimeMock = mock(LocalDateTime.class);
        (when(registrationInfoMock.getBirthDate())).thenReturn(localDateTimeMock);
        (when(registrationInfoMock.getCity())).thenReturn("XZ");
        (when(registrationInfoMock.getAvatarLink())).thenReturn("10");
        (when(registrationInfoMock.getInfo())).thenReturn("#$\\\"'");
        (when(registrationInfoMock.getHardSkills())).thenReturn("-3");

        userService.updateUser(-32769L, registrationInfoMock);
    }
    
    @Test
    @DisplayName("updateUser: userId = 1024 (mutated from zero), registrationInfo = mock() -> throw RuntimeException")
    public void testUpdateUserThrowsRE() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.DELETE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);

        assertThrows(RuntimeException.class, () -> userService.updateUser(1024L, registrationInfoMock));
    }

    @Test
    @DisplayName("updateUser: userId = -65 (mutated from -1), registrationInfo = mock() -> throw RuntimeException")
    public void testUpdateUserThrowsRE1() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.ACTIVE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);
        (when(userRepositoryMock.existsByEmailAndUserStateAndIdNot(any(), any(), any()))).thenReturn(false);
        (when(userRepositoryMock.existsByPhoneAndUserStateAndIdNot(any(), any(), any()))).thenReturn(true);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("10");
        (when(registrationInfoMock.getPhone())).thenReturn("");

        assertThrows(RuntimeException.class, () -> userService.updateUser(-65L, registrationInfoMock));
    }

    @Test
    @DisplayName("updateUser: userId = -9223372036854775807 (mutated from positive), registrationInfo = mock() -> throw RuntimeException")
    public void testUpdateUserThrowsRE2() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.ACTIVE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);
        (when(userRepositoryMock.existsByEmailAndUserStateAndIdNot(any(), any(), any()))).thenReturn(true);
        RegistrationInfo registrationInfoMock = mock(RegistrationInfo.class);
        (when(registrationInfoMock.getEmail())).thenReturn("#$\\\"'");

        assertThrows(RuntimeException.class, () -> userService.updateUser(-9223372036854775807L, registrationInfoMock));
    }
    
    @Test
    @DisplayName("validateUserState: userId = -8193 (mutated from -1)")
    public void testValidateUserState() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock1.getUserState())).thenReturn(userStateMock);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);

        UserEntity actual = userService.validateUserState(-8193L);

        assertSame(userEntityMock1, actual);
    }
    
    @Test
    @DisplayName("validateUserState: userId = 4 (mutated from zero) -> throw RuntimeException")
    public void testValidateUserStateThrowsRE() throws Throwable {
        Optional optionalMock = mock(Optional.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(optionalMock.orElseThrow(any()))).thenReturn(userEntityMock);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userState = UserState.DELETE;
        (when(userEntityMock1.getUserState())).thenReturn(userState);
        (when(optionalMock.get())).thenReturn(userEntityMock1);
        (when(userRepositoryMock.findById(any()))).thenReturn(optionalMock);

        assertThrows(RuntimeException.class, () -> userService.validateUserState(4L));
    }

    @BeforeEach
    public void setUp() {
        mockitoCloseable = openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockitoCloseable.close();
    }
}
