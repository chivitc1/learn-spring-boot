package com.example.retroapp.web;

import com.example.retroapp.models.Comment;
import com.example.retroapp.models.CommentType;
import com.example.retroapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RolesAllowed({"ROLE_HOST", "ROLE_MEMBER"})
@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("time", LocalDateTime.now().format(dateTimeFormatter));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allComments.stream()
                .collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("column1Comments", groupedComments.get(CommentType.COLUMN_1));
        model.addAttribute("column2Comments", groupedComments.get(CommentType.COLUMN_2));
        model.addAttribute("column3Comments", groupedComments.get(CommentType.COLUMN_3));

        log.info("Allcomments: [{}], \nModel: [{}]", allComments, model);
        return "comment";
    }

    @PostMapping("/comment")
    public String createComment(@RequestParam(name = "column1Comment", required = false) String column1Comment,
                                @RequestParam(name = "column2Comment", required = false) String column2Comment,
                                @RequestParam(name = "column3Comment", required = false) String column3Comment) {
        log.info("User submit request: column1Comment=[{}], column2Comment=[{}], column3Comment=[{}]",
                column1Comment, column2Comment, column3Comment);
        List<Comment> comments = new ArrayList<>();

        if (!StringUtils.isEmpty(column1Comment)) {
            comments.add(getComment(column1Comment, CommentType.COLUMN_1));
        }

        if (!StringUtils.isEmpty(column2Comment)) {
            comments.add(getComment(column2Comment, CommentType.COLUMN_2));
        }

        if (!StringUtils.isEmpty(column3Comment)) {
            comments.add(getComment(column3Comment, CommentType.COLUMN_3));
        }

        if (!comments.isEmpty()) {
            log.info("Saved {}", commentService.saveAll(comments));
        }

        return "redirect:/";
    }

    private Comment getComment(String comment, CommentType commentType) {
        return Comment.builder()
                .type(commentType)
                .comment(comment)
                .build();
    }
}
