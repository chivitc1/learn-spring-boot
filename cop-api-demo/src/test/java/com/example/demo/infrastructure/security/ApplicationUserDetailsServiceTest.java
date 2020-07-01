package com.example.demo.infrastructure.security;

import com.example.demo.user.UserRepository;
import com.example.demo.user.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationUserDetailsServiceTest {

    @InjectMocks
    ApplicationUserDetailsService userDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmailIgnoreCase(Users.OFFICER_EMAIL))
                .thenReturn(Optional.of(Users.officer()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(Users.OFFICER_EMAIL);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(Users.OFFICER_EMAIL);
        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority).contains("ROLE_OFFICER");
    }

    @Test
    public void loadUserByUsername_notFound() {
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            userDetailsService.loadUserByUsername("a@notexist.com");
        }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with email a@notexist.com could not be found");
    }
}