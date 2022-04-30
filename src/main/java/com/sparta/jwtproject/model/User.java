package com.sparta.jwtproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String userImgUrl;

    @Column
    private String userTitle;

    public User(String username, String encodedPassword, String nickname, String userImageUrl, String userTitle) {
        this.username = username;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
    }

}
