package com.example.demo.user;

import com.example.demo.entitybase.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "cop_user")
@EqualsAndHashCode(of = "id")
final public class User extends AbstractEntity<UserId> {

    @Builder
    public User(UserId id, String email, String password, Set<UserRole> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
//    @NotNull
    private Set<UserRole> roles;

    public static User createOfficer(UserId userId, String email, String password) {
        HashSet<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.OFFICER);
        return new User(userId, email, password, userRoles);
    }

    public static User createCaptain(UserId userId, String email, String password) {
        HashSet<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.CAPTAIN);
        return new User(userId, email, password, userRoles);
    }
}
