package com.sparta.jwtproject.dto;

import lombok.Getter;

@Getter
public class NaverUserInfoDto {
    private String username;
    private String nickname;

    public NaverUserInfoDto(String username, String nickname) {
        this.username=username;
        this.nickname=nickname;
    }
}
