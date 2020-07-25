package com.example.retroapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "retro_user")
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
//    @SequenceGenerator(name = "retro_user_id_seq_generator", sequenceName = "retro_user_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retro_user_id_seq_generator")
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}
