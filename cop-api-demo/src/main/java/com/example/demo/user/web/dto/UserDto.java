package com.example.demo.user.web.dto;

import com.example.demo.user.User;
import com.example.demo.user.UserId;
import com.example.demo.user.UserRole;
import lombok.Value;

import java.util.Set;

@Value
public class UserDto {
    private final UserId id;
    private final String email;
    private final Set<UserRole> roles;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getRoles());
    }
}
