package ru.pilot.skillbox.socnet.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pilot.skillbox.socnet.users.entity.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> getAllByOwnerId(Long ownerUserId);
    List<SubscriptionEntity> getAllBySubscriberId(Long subscriberUserId);
    
    void deleteByOwnerIdAndSubscriberId(Long ownerUserId, Long subscriberUserId);
}
