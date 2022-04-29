package com.sparta.jwtproject.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String userImageUrl;

    @Column(nullable = false)
    private Long userExp;

    @Column(nullable = false)
    private Long userLevel;

    @Column(nullable = false)
    private Long totalPrice;

    public User(String kakaousername, String password, String nickname, String userImageUrl, Long userExp, Long userLevel, Long totalPrice) {
        this.username = kakaousername;
        this.password = password;
        this.nickname = nickname;
        this.userImageUrl = userImageUrl;
        this.userExp = userExp;
        this.userLevel = userLevel;
        this.totalPrice = totalPrice;
    }

    @Builder
    public User(String username, String password, String nickname) {
        this.username=username;
        this.password=password;
        this.nickname=nickname;
    }

}

