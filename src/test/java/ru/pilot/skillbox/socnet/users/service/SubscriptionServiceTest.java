package ru.pilot.skillbox.socnet.users.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.entity.SubscriptionEntity;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;
import ru.pilot.skillbox.socnet.users.repository.SubscriptionRepository;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.utbot.runtime.utils.java.UtUtils.deepEquals;

public final class SubscriptionServiceTest {
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private UserService userServiceMock;

    @Mock
    private SubscriptionRepository subscriptionRepositoryMock;

    private AutoCloseable mockitoCloseable;
    
    @Test
    @DisplayName("addSubscription: ownerUserId = max, subscriberUserId = 72057594037927937 (mutated from positive)")
    public void testAddSubscriptionWithCornerCase() {
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(userServiceMock.validateUserState(any()))).thenReturn(userEntityMock);
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        (when(subscriptionRepositoryMock.save(any()))).thenReturn(subscriptionEntityMock);

        subscriptionService.addSubscription(Long.MAX_VALUE, 72057594037927937L);
    }

    @Test
    @DisplayName("addSubscription: ownerUserId = max (mutated from -1), subscriberUserId = -1")
    public void testAddSubscriptionWithCornerCase1() {
        UserEntity userEntityMock = mock(UserEntity.class);
        (when(userServiceMock.validateUserState(any()))).thenReturn(userEntityMock);
        (when(subscriptionRepositoryMock.save(any()))).thenReturn(null);
        subscriptionService.addSubscription(Long.MAX_VALUE, -1L);
    }
    
    @Test
    @DisplayName("addSubscription: ownerUserId = -1 (mutated from max), subscriberUserId = -1 -> throw RuntimeException")
    public void testAddSubscriptionThrowsRE() {
        assertThrows(RuntimeException.class, () -> subscriptionService.addSubscription(-1L, -1L));
    }
    
    @Test
    @DisplayName("deleteSubscription: ownerUserId != null : True -> ownerUserId.equals(subscriberUserId) : False")
    public void testDeleteSubscription_NotOwnerUserIdEquals() {
        (when(userServiceMock.validateUserState(any()))).thenReturn(((UserEntity) null), ((UserEntity) null));
        (((SubscriptionRepository) (doNothing()).when(subscriptionRepositoryMock))).deleteByOwnerIdAndSubscriberId(any(), any());
        Long ownerUserId = -255L;
        Long subscriberUserId = -128L;
        subscriptionService.deleteSubscription(ownerUserId, subscriberUserId);
    }

    @Test
    @DisplayName("deleteSubscription: subscriptionValidate -> ownerUserId != null : False")
    public void testDeleteSubscription_OwnerUserIdEqualsNull() {
        (when(userServiceMock.validateUserState(any()))).thenReturn(((UserEntity) null), ((UserEntity) null));
        (((SubscriptionRepository) (doNothing()).when(subscriptionRepositoryMock))).deleteByOwnerIdAndSubscriberId(any(), any());
        subscriptionService.deleteSubscription(null, null);
    }
   
    @Test
    @DisplayName("deleteSubscription: ownerUserId = -1 (mutated from max), subscriberUserId = -1 -> throw RuntimeException")
    public void testDeleteSubscriptionThrowsRE() {
        assertThrows(RuntimeException.class, () -> subscriptionService.deleteSubscription(-1L, -1L));
    }
   
    @Test
    @DisplayName("getSubscribers: subscriberUserId = -1 (mutated from max)")
    public void testGetSubscribers() {
        LinkedList<SubscriptionEntity> linkedList = new LinkedList<>();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getOwner())).thenReturn(userEntityMock);
        linkedList.add(subscriptionEntityMock);
        (when(subscriptionRepositoryMock.getAllBySubscriberId(any()))).thenReturn(linkedList);

        ArrayList<UserShortDto> actual = ((ArrayList<UserShortDto>) subscriptionService.getSubscribers(-1L));

        ArrayList<UserShortDto> expected = new ArrayList<>();

        assertTrue(deepEquals(expected, actual));
    }

    
    @Test
    @DisplayName("getSubscribers: subscriberUserId = -4194305 (mutated from -1) -> return null")
    public void testGetSubscribers1() {
        List listMock = mock(List.class);
        (when(listMock.isEmpty())).thenReturn(false);
        Stream streamMock = mock(Stream.class);
        Stream streamMock1 = mock(Stream.class);
        Stream streamMock2 = mock(Stream.class);
        Stream streamMock3 = mock(Stream.class);
        (when(streamMock3.collect(any()))).thenReturn(null);
        (when(streamMock2.map(any()))).thenReturn(streamMock3);
        (when(streamMock1.filter(any()))).thenReturn(streamMock2);
        (when(streamMock.map(any()))).thenReturn(streamMock1);
        (when(listMock.stream())).thenReturn(streamMock);
        (when(subscriptionRepositoryMock.getAllBySubscriberId(any()))).thenReturn(listMock);

        List<UserShortDto> actual = subscriptionService.getSubscribers(-4194305L);

        assertNull(actual);
    }

    @Test
    @DisplayName("getSubscribers: subscriberUserId = 274877906945 (mutated from positive)")
    public void testGetSubscribers2() {
        ArrayList arrayList = new ArrayList();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getOwner())).thenReturn(userEntityMock);
        arrayList.add(subscriptionEntityMock);
        SubscriptionEntity subscriptionEntityMock1 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userStateMock1 = mock(UserState.class);
        (when(userEntityMock1.getUserState())).thenReturn(userStateMock1);
        (when(subscriptionEntityMock1.getOwner())).thenReturn(userEntityMock1);
        arrayList.add(subscriptionEntityMock1);
        (when(subscriptionRepositoryMock.getAllBySubscriberId(any()))).thenReturn(arrayList);

        ArrayList actual = ((ArrayList) subscriptionService.getSubscribers(274877906945L));

        ArrayList expected = new ArrayList();

        assertTrue(deepEquals(expected, actual));
    }

    @Test
    @DisplayName("getSubscribers: subscriberUserId = -9223372036854775807 (mutated from positive)")
    public void testGetSubscribers3() {
        LinkedList linkedList = new LinkedList();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getOwner())).thenReturn(userEntityMock);
        linkedList.add(subscriptionEntityMock);
        SubscriptionEntity subscriptionEntityMock1 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userStateMock1 = mock(UserState.class);
        (when(userEntityMock1.getUserState())).thenReturn(userStateMock1);
        (when(subscriptionEntityMock1.getOwner())).thenReturn(userEntityMock1);
        linkedList.add(subscriptionEntityMock1);
        SubscriptionEntity subscriptionEntityMock2 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock2 = mock(UserEntity.class);
        UserState userStateMock2 = mock(UserState.class);
        (when(userEntityMock2.getUserState())).thenReturn(userStateMock2);
        (when(subscriptionEntityMock2.getOwner())).thenReturn(userEntityMock2);
        linkedList.add(subscriptionEntityMock2);
        (when(subscriptionRepositoryMock.getAllBySubscriberId(any()))).thenReturn(linkedList);

        ArrayList actual = ((ArrayList) subscriptionService.getSubscribers(-9223372036854775807L));

        ArrayList expected = new ArrayList();

        assertTrue(deepEquals(expected, actual));
    }
   
    @Test
    public void testGetSubscribers4() {
        ArrayList arrayList = new ArrayList();
        (when(subscriptionRepositoryMock.getAllBySubscriberId(any()))).thenReturn(arrayList);
        List actual = subscriptionService.getSubscribers(null);
        List expected = new ArrayList();
        assertTrue(deepEquals(expected, actual));
    }
   
    @Test
    @DisplayName("getSubscription: ownerUserId = -1 (mutated from max)")
    public void testGetSubscription() {
        LinkedList linkedList = new LinkedList();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getSubscriber())).thenReturn(userEntityMock);
        linkedList.add(subscriptionEntityMock);
        (when(subscriptionRepositoryMock.getAllByOwnerId(any()))).thenReturn(linkedList);

        ArrayList actual = ((ArrayList) subscriptionService.getSubscription(-1L));

        ArrayList expected = new ArrayList();

        assertTrue(deepEquals(expected, actual));
    }

    @Test
    @DisplayName("getSubscription: ownerUserId = -4194305 (mutated from -1) -> return null")
    public void testGetSubscription1() {
        List listMock = mock(List.class);
        (when(listMock.isEmpty())).thenReturn(false);
        Stream streamMock = mock(Stream.class);
        Stream streamMock1 = mock(Stream.class);
        Stream streamMock2 = mock(Stream.class);
        Stream streamMock3 = mock(Stream.class);
        (when(streamMock3.collect(any()))).thenReturn(null);
        (when(streamMock2.map(any()))).thenReturn(streamMock3);
        (when(streamMock1.filter(any()))).thenReturn(streamMock2);
        (when(streamMock.map(any()))).thenReturn(streamMock1);
        (when(listMock.stream())).thenReturn(streamMock);
        (when(subscriptionRepositoryMock.getAllByOwnerId(any()))).thenReturn(listMock);

        List actual = subscriptionService.getSubscription(-4194305L);

        assertNull(actual);
    }
    
    @Test
    @DisplayName("getSubscription: ownerUserId = -9223372036854775807 (mutated from positive)")
    public void testGetSubscription2() {
        LinkedList linkedList = new LinkedList();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getSubscriber())).thenReturn(userEntityMock);
        linkedList.add(subscriptionEntityMock);
        SubscriptionEntity subscriptionEntityMock1 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userStateMock1 = mock(UserState.class);
        (when(userEntityMock1.getUserState())).thenReturn(userStateMock1);
        (when(subscriptionEntityMock1.getSubscriber())).thenReturn(userEntityMock1);
        linkedList.add(subscriptionEntityMock1);
        SubscriptionEntity subscriptionEntityMock2 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock2 = mock(UserEntity.class);
        UserState userStateMock2 = mock(UserState.class);
        (when(userEntityMock2.getUserState())).thenReturn(userStateMock2);
        (when(subscriptionEntityMock2.getSubscriber())).thenReturn(userEntityMock2);
        linkedList.add(subscriptionEntityMock2);
        (when(subscriptionRepositoryMock.getAllByOwnerId(any()))).thenReturn(linkedList);
        ArrayList actual = ((ArrayList) subscriptionService.getSubscription(-9223372036854775807L));
        ArrayList expected = new ArrayList();
        assertTrue(deepEquals(expected, actual));
    }

   
    @Test
    @DisplayName("getSubscription: ownerUserId = positive (mutated from -9223372036854775807)")
    public void testGetSubscription3() {
        LinkedList linkedList = new LinkedList();
        SubscriptionEntity subscriptionEntityMock = mock(SubscriptionEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        UserState userStateMock = mock(UserState.class);
        (when(userEntityMock.getUserState())).thenReturn(userStateMock);
        (when(subscriptionEntityMock.getSubscriber())).thenReturn(userEntityMock);
        linkedList.add(subscriptionEntityMock);
        SubscriptionEntity subscriptionEntityMock1 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock1 = mock(UserEntity.class);
        UserState userStateMock1 = mock(UserState.class);
        (when(userEntityMock1.getUserState())).thenReturn(userStateMock1);
        (when(subscriptionEntityMock1.getSubscriber())).thenReturn(userEntityMock1);
        linkedList.add(subscriptionEntityMock1);
        SubscriptionEntity subscriptionEntityMock2 = mock(SubscriptionEntity.class);
        UserEntity userEntityMock2 = mock(UserEntity.class);
        UserState userStateMock2 = mock(UserState.class);
        (when(userEntityMock2.getUserState())).thenReturn(userStateMock2);
        (when(subscriptionEntityMock2.getSubscriber())).thenReturn(userEntityMock2);
        linkedList.add(subscriptionEntityMock2);
        (when(subscriptionRepositoryMock.getAllByOwnerId(any()))).thenReturn(linkedList);

        ArrayList actual = ((ArrayList) subscriptionService.getSubscription(1L));

        ArrayList expected = new ArrayList();

        assertTrue(deepEquals(expected, actual));
    }
   
    @Test
    public void testGetSubscription4() {
        ArrayList arrayList = new ArrayList();
        (when(subscriptionRepositoryMock.getAllByOwnerId(any()))).thenReturn(arrayList);
        List actual = subscriptionService.getSubscription(null);
        List expected = new ArrayList();
        assertTrue(deepEquals(expected, actual));
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
