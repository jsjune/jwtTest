package service;

import com.sparta.jwtproject.dto.ResponseDto;
import com.sparta.jwtproject.dto.SignUpRequestDto;
import com.sparta.jwtproject.model.User;
import com.sparta.jwtproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDto registerUser(SignUpRequestDto requestDto) {
        Boolean result = true;
        String err_msg = "사용가능한 ID 입니다.";
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        Optional<User> found = userRepository.findByUsername(username);

        if (found.isPresent()) {
            err_msg = "중복된 ID가 존재합니다.";
            result = false;
            return new ResponseDto(result, err_msg, nickname);
        }
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(username, password, nickname);
        userRepository.save(user);

        ResponseDto responseDto = new ResponseDto(result, err_msg, nickname);
        return responseDto;

    }
}
