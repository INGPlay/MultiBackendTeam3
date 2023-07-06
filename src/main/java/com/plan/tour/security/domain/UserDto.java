package com.plan.tour.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
}
