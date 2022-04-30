package com.sparta.jwtproject.dto;

import lombok.Getter;

@Getter
public class SocialLoginDto {
    private String username;
    private String nickname;

    public SocialLoginDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
