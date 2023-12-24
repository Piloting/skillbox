package ru.pilot.skillbox.socnet.users.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static ru.pilot.skillbox.socnet.users.entity.SubscriptionEntity.TABLE;

@Entity
@Table(name = TABLE)
@Getter
@Setter
@ToString
public class SubscriptionEntity {
    public static final String TABLE = "sn_subscription";
    public static final String TABLE_SEQ = "sn_subscription_seq";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private UserEntity subscriber;

    @Column
    private SubscribeState subscribeState;

    @Column
    private LocalDateTime createDate;
}
