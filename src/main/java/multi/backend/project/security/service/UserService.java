package multi.backend.project.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.domain.UserDto;
import multi.backend.project.security.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private void insertUser(UserDto userDto){

        // 암호화 후 저장
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userMapper.insertUser(userDto);
    }

    public void registerUser(RegisterDto registerDto){
        UserDto userDto = new UserDto(
                registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), registerDto.getPhone(), "ROLE_USER"
        );

        insertUser(userDto);
    }

    public void registerAdmin(RegisterDto registerDto){
        UserDto userDto = new UserDto(
                registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), registerDto.getPhone(), "ROLE_ADMIN"
        );

        insertUser(userDto);
    }
}
