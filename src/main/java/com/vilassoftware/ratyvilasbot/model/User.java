package com.vilassoftware.ratyvilasbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "usersDataTable")
@Getter
@Setter
@ToString
public class User {
    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private Integer userUtc;

    private String userPhone;

    private LocalDateTime registeredAt;

    @JsonIgnore
    @OneToMany(cascade=ALL, mappedBy="user")
    private List<NotificationTask> reminders;

}
