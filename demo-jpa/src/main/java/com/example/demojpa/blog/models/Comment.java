package com.example.demojpa.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "blog_comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@EqualsAndHashCode(of = {"author", "createdOn"})
final public class Comment implements Serializable {

    @Id
    @SequenceGenerator(name = "comment_id_seq_generator", sequenceName = "comment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq_generator")
    @Column(name = "id", updatable = false)
    private Long id;

    private String review;
    private String author;
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "post_id" )
    private Post post;
}
