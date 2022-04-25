package com.sparta.jwtproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseDto {
    private boolean result;
    private String err_msg;
    private String nickname;

    public ResponseDto(Boolean result, String err_msg, String nickname) {
        this.result = result;
        this.err_msg = err_msg;
        this.nickname = nickname;
    }

    public ResponseDto(Boolean result) {
        this.result = result;
    }

    public ResponseDto(String nickname, Boolean result) {
        this.nickname = nickname;
        this.result = result;
    }

    public ResponseDto(String nickname) {
        this.nickname=nickname;
    }

    public ResponseDto(Boolean result, String err_msg) {
        this.result=result;
        this.err_msg=err_msg;
    }



}
