package ru.pilot.skillbox.socnet.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pilot.skillbox.socnet.users.entity.UserEntity;
import ru.pilot.skillbox.socnet.users.entity.UserState;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByPhoneAndUserState(String phone, UserState state);
    boolean existsByEmailAndUserState(String email, UserState state);
    
    boolean existsByPhoneAndUserStateAndIdNot(String phone, UserState state, Long id);
    boolean existsByEmailAndUserStateAndIdNot(String email, UserState state, Long id);
    
    Page<UserEntity> findAllBySurnameAndCity(String surname, String city, Pageable pageable);
    Page<UserEntity> findAllBySurname(String surname, Pageable pageable);
    Page<UserEntity> findAllByCity(String city, Pageable pageable);
}
