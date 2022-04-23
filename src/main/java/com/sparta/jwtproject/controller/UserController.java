package com.sparta.jwtproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.jwtproject.dto.KakaoUserInfoDto;
import com.sparta.jwtproject.dto.ResponseDto;
import com.sparta.jwtproject.dto.SignUpRequestDto;
import com.sparta.jwtproject.security.UserDetailsImpl;
import com.sparta.jwtproject.service.GoogleUserService;
import com.sparta.jwtproject.service.KakaoUserService;
import com.sparta.jwtproject.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;

    public UserController(UserService userService, KakaoUserService kakaoUserService, GoogleUserService googleUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
        this.googleUserService = googleUserService;
    }

    @PostMapping("/user/signup")
    public ResponseDto signup(@RequestBody SignUpRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    @GetMapping("/user/isLogin")
    public ResponseDto home(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseDto(userDetails.getNickname());
    }

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public KakaoUserInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code, response);
    }

    // 구글 로그인
    @GetMapping("/user/google/callback")
    public void googleLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
        googleUserService.googleLogin(code, response);
    }
}