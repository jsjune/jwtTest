package com.sparta.jwtproject.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String username;
    private String nickname;
    private String password;
}
