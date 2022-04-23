package com.sparta.jwtproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.jwtproject.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class FormLoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        ResponseDto responseDto = new ResponseDto(false,"잘못된 아이디 혹은 비밀번호 입니다");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = response.getOutputStream();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, responseDto);
        out.flush();
    }
}
