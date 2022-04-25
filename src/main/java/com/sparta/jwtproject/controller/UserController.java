package com.sparta.jwtproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.jwtproject.dto.KakaoUserInfoDto;
import com.sparta.jwtproject.dto.ResponseDto;
import com.sparta.jwtproject.dto.SignUpRequestDto;
import com.sparta.jwtproject.security.UserDetailsImpl;
import com.sparta.jwtproject.service.GoogleUserService;
import com.sparta.jwtproject.service.KakaoUserService;
import com.sparta.jwtproject.service.NaverUserService;
import com.sparta.jwtproject.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;
    private final NaverUserService naverUserService;

    @PostMapping("/user/signup")
    public ResponseDto signup(@RequestBody SignUpRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    @GetMapping("/user/loginCheck")
    public ResponseDto home(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseDto(userDetails.getNickname());
    }

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public KakaoUserInfoDto kakaoLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
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

    // 네이버 로그인
    @GetMapping("/user/naver/callback")
    public void naverLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response
    ) throws JsonProcessingException {
        naverUserService.naverLogin(code, state, response);
    }
}