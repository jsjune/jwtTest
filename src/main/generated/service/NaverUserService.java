package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.jwtproject.dto.NaverUserInfoDto;
import com.sparta.jwtproject.model.User;
import com.sparta.jwtproject.repository.UserRepository;
import com.sparta.jwtproject.security.UserDetailsImpl;
import com.sparta.jwtproject.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverUserService {

//    @Value("${naver.client-id}")
//    String naverClientId;
//
//    @Value("${naver.client-secret}")
//    String naverClientSecret;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 네이버 로그인
    public void naverLogin(String code, String state, HttpServletResponse response) throws JsonProcessingException {

        // 1. 인가코드로 엑세스토큰 가져오기
        String accessToken = getAccessToken(code, state);

        // 2. 엑세스토큰으로 유저정보 가져오기
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);

        // 3. 유저확인 & 회원가입
        User naverUser = getUser(naverUserInfo);

        // 4. 시큐리티 강제 로그인
        Authentication authentication = securityLogin(naverUser);

        //5. jwt 토큰 발급
        jwtToken(authentication, response);
    }

    // 1. 인가코드로 엑세스토큰 가져오기
    private String getAccessToken(String code, String state) throws JsonProcessingException {

        // 헤더에 Content-type 지정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 바디에 필요한 정보 담기
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "oq32J_8jgLtjcSRvYUO4");
        body.add("client_secret", "dc6LwAfBEL");
        body.add("code", code);
        body.add("state", state);

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> naverToken = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST, naverToken,
                String.class
        );

        // response에서 엑세스토큰 가져오기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);
        String accessToken = responseToken.get("access_token").asText();
        return accessToken;
    }

    // 2. 엑세스토큰으로 유저정보 가져오기
    private NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {

        // 헤더에 엑세스토큰 담기, Content-type 지정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> naverUser = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST, naverUser,
                String.class
        );

        // response에서 유저정보 가져오기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String provider = "naver";
        String username = provider + "_" + jsonNode.get("response").get("id").asText();
        String nickname = jsonNode.get("response").get("nickname").asText();

        return new NaverUserInfoDto(username, nickname);
    }

    // 3. 유저확인 & 회원가입
    private User getUser(NaverUserInfoDto naverUserInfo) {

        String naverusername =naverUserInfo.getUsername();
        User naverUser = userRepository.findByUsername(naverusername)
                .orElse(null);

        if (naverUser == null) {
            // 회원가입
            // username: kakao nickname
            String nickname = naverUserInfo.getNickname();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            String userImageUrl="없음";
            Long userExp=0L;
            Long userLevel=0L;
            Long totalPrice=0L;

            naverUser = new User(naverusername, encodedPassword,nickname,userImageUrl,userExp,userLevel,totalPrice);
            userRepository.save(naverUser);

        }
        return naverUser;
    }

    // 시큐리티 강제 로그인
    private Authentication securityLogin(User foundUser) {
        UserDetails userDetails = new UserDetailsImpl(foundUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // jwt 토큰 발급
    private void jwtToken(Authentication authentication,HttpServletResponse response) {

        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String token = JwtTokenUtils.generateJwtToken(userDetailsImpl);
        response.addHeader("Authorization", "BEARER" + " " + token);

    }
}
