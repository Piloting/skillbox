package ru.pilot.skillbox.socnet.users.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pilot.skillbox.socnet.users.controller.dto.UserShortDto;
import ru.pilot.skillbox.socnet.users.entity.SubscribeState;
import ru.pilot.skillbox.socnet.users.entity.SubscriptionEntity;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;
import ru.pilot.skillbox.socnet.users.exception.UserViolationException;
import ru.pilot.skillbox.socnet.users.exception.dto.Violation;
import ru.pilot.skillbox.socnet.users.mapper.DtoMapper;
import ru.pilot.skillbox.socnet.users.repository.SubscriptionRepository;

/**
 * Сервис для работы с подписками
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;
    
    /**
     * Просмотр списка ПОДПИСЧИКОВ пользователя 
     * т.е. полечение тех, кто подписался на указанного пользователя 
     */
    public List<UserShortDto> getSubscribers(Long subscriberUserId) {
        List<SubscriptionEntity> subscriberList = subscriptionRepository.getAllBySubscriberId(subscriberUserId);
        if (CollectionUtils.isEmpty(subscriberList)){
            return Collections.emptyList();
        }
        return subscriberList.stream()
                .map(SubscriptionEntity::getOwner)
                .filter(this::isActive)
                .map(DtoMapper::entityToUserShort)
                .collect(Collectors.toList());
    }

    private boolean isActive(UserEntity entity){
        return entity.getUserState() == UserState.ACTIVE;
    }

    /**
     * Просмотр списка ПОДПИСОК пользователя
     * т.е. получение тех на кого подписан указанный пользователь
     */
    public List<UserShortDto> getSubscription(Long ownerUserId) {
        List<SubscriptionEntity> subscriptionList = subscriptionRepository.getAllByOwnerId(ownerUserId);
        if (CollectionUtils.isEmpty(subscriptionList)){
            return Collections.emptyList();
        }
        return subscriptionList.stream()
                .map(SubscriptionEntity::getSubscriber)
                .filter(this::isActive)
                .map(DtoMapper::entityToUserShort)
                .collect(Collectors.toList());
    }

    /** Добавление новой подписки */
    @Transactional
    public void addSubscription(Long ownerUserId, Long subscriberUserId) {
        subscriptionValidate(ownerUserId, subscriberUserId);
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setOwner(new UserEntity(ownerUserId));
        subscription.setSubscriber(new UserEntity(subscriberUserId));
        subscription.setCreateDate(LocalDateTime.now());
        subscription.setSubscribeState(SubscribeState.ACTIVE);
        subscriptionRepository.save(subscription);
    }

    private void subscriptionValidate(Long ownerUserId, Long subscriberUserId) {
        if (ownerUserId != null && ownerUserId.equals(subscriberUserId)){
            throw new UserViolationException(new Violation("ownerUserId", "Невозможно подписаться на самого себя"));
        }
        userService.validateUserState(ownerUserId);
        userService.validateUserState(subscriberUserId);
    }

    /** Удаление подписки */
    @Transactional
    public void deleteSubscription(Long ownerUserId, Long subscriberUserId) {
        subscriptionValidate(ownerUserId, subscriberUserId);
        subscriptionRepository.deleteByOwnerIdAndSubscriberId(ownerUserId, subscriberUserId);
    }

}
