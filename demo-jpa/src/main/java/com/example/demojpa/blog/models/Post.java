package com.example.demojpa.blog.models;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_post")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(of = {"author", "createdOn"})
@Builder
final public class Post implements Serializable {

    @Id
    @SequenceGenerator(name = "post_id_seq_generator", sequenceName = "post_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq_generator")
    @Column(name = "id", updatable = false)
    private Long id;

    private String title;
    private String content;
    private String author;

    @CreatedDate
    private Timestamp createdOn;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public static Post postArticle(Long id, String author, String title, String content) {
        return Post.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}
