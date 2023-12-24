package ru.pilot.skillbox.socnet.users.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static ru.pilot.skillbox.socnet.users.entity.UserEntity.TABLE;

@Entity
@Table(name = TABLE)
@Getter @Setter @ToString
@NoArgsConstructor
public class UserEntity {
    public static final String TABLE = "sn_user";
    public static final String TABLE_SEQ = "sn_user_seq";
    
    public UserEntity(Long id){
        this.id = id;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQ)
    @SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
    private Long id;

    @Column
    private String email;
    @Column
    private String phone;

    @Column
    private UserState userState;
    
    @Column
    private LocalDateTime createDate;
    
    @Column
    private String surname;
    @Column
    private String firstname;
    @Column
    private String patronymic;
    
    @Column
    private Gender gender;

    @Column
    private LocalDateTime birthDate;
    
    @Column
    private String city;
    
    @Column
    private String avatarLink;
    
    @Column
    private String info;
    
    @Column
    private String hardSkills;
}
