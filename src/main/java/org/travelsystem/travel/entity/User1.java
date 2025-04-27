package org.travelsystem.travel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

// User.java
@Entity
@Table(name = "user1")
@Data
public class User1 {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String phone;

    private String password;

    @Column(name = "wechat_openid", unique = true)
    private String wechatOpenid;

    private String nickname;
    private String avatar;
    private Integer gender;
    private LocalDate birthday;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String email;
    private Integer status;
}