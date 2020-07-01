package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User createOfficer(String email, String password) {
        User user = User.createOfficer(repository.nextId(), email, passwordEncoder.encode(password));

        return repository.save(user);
    }

    public Optional<User> getUser(UserId userId) {
        return repository.findById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }
}
