package com.sparta.jwtproject.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String userEmail;
    private String password;
    private String username;
    private String nickname;
}
