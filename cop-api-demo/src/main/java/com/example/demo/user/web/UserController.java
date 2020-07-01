package com.example.demo.user.web;

import com.example.demo.infrastructure.security.ApplicationUserDetails;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import com.example.demo.user.web.dto.CreateOfficerParams;
import com.example.demo.user.web.dto.UserDto;
import com.example.demo.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
        User user = service.getUser(userDetails.getUserId()).orElseThrow(() ->
                new UserNotFoundException(userDetails.getUserId()));
        return UserDto.fromUser(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createOfficer(@Valid @RequestBody CreateOfficerParams params) {
        User officer = service.createOfficer(params.getEmail(), params.getPassword());
        return UserDto.fromUser(officer);
    }
}
