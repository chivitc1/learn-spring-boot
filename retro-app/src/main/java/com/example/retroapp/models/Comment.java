package com.example.retroapp.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "retro_comment")
@Data
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @Enumerated(EnumType.STRING)
    private CommentType type;

    @Builder
    public Comment(Long id, String comment, CommentType type, LocalDateTime createdDate, String createdBy) {
        super(createdDate, createdBy);
        this.id = id;
        this.comment = comment;
        this.type = type;
    }
}
