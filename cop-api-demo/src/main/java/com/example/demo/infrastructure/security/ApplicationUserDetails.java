package com.example.demo.infrastructure.security;

import com.example.demo.user.User;
import com.example.demo.user.UserId;
import com.example.demo.user.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUserDetails extends org.springframework.security.core.userdetails.User {
    private static final String ROLE_PREFIX = "ROLE_";

    @Getter
    private final UserId userId;

    public ApplicationUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), createAuthoriries(user.getRoles()));
        this.userId = user.getId();
    }

    private static Collection<? extends GrantedAuthority> createAuthoriries(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.name()))
                .collect(Collectors.toSet());
    }
}
