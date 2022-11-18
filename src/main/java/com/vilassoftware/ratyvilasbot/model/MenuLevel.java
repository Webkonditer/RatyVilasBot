package com.vilassoftware.ratyvilasbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "menu_level")
public class MenuLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int parentPosition;

    private int level;

    private int position;

    private int buttonSize;

    private String buttonText;

    @Column(columnDefinition="TEXT")
    private String text;

}
