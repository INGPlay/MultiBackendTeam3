package com.plan.tour.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.security.domain.RegisterDto;
import com.plan.tour.security.domain.UserDto;
import com.plan.tour.security.domain.form.UpdatePasswordForm;
import com.plan.tour.security.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUserByUsername(String username){
        return userMapper.selectUser(username);
    }

    public UserDto getUserByUserEmail(String useremail){
        return userMapper.selectUserByUserEmail(useremail);
    }

    public UserDto getUserByUserPhone(String userphone){
        return userMapper.selectUserByUserPhone(userphone);
    }

    private void insertUser(UserDto userDto){

        // 암호화 후 저장
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userMapper.insertUser(userDto);
    }

    public boolean checkUserPassword(String username, String password){

        UserDto userDto = getUserByUsername(username);

        return passwordEncoder.matches(password, userDto.getPassword());
    }

    public void updatePassword(String username, UpdatePasswordForm updatePasswordForm){
        userMapper.updateUserPassword(username, passwordEncoder.encode(updatePasswordForm.getNewPassword()));
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
