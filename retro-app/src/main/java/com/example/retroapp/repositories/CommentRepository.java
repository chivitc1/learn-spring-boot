package com.example.retroapp.repositories;

import com.example.retroapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE year(c.createdDate) = :year AND month(c.createdDate) = :month AND day(c.createdDate) = :day")
    List<Comment> findByCreatedYearAndMonthAndDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);
}
