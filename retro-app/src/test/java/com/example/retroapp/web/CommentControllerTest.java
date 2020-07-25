package com.example.retroapp.web;

import com.example.retroapp.models.Comment;
import com.example.retroapp.models.CommentType;
import com.example.retroapp.models.RoleType;
import com.example.retroapp.services.CommentService;
import com.example.retroapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @MockBean
    UserService userService;

    @Test
    void saveComments_happyPath_shouldReturnStatus302() throws Exception{
        // When
        ResultActions resultActions =
                mockMvc.perform(post("/comment")
                        .with(csrf())
                        .with(user("test_user").roles("MEMBER"))
                        .param("column1Comment", "Test Column1 comment"));

        // Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentService);

    }

    @Test
    void getComments_happyPath_shouldReturnStatus200() throws Exception {
        //Given
        Comment comment = Comment.builder()
                .comment("Test comment column1")
                .type(CommentType.COLUMN_1)
                .createdBy("test_user")
                .createdDate(LocalDateTime.now())
                .build();
        Comment comment2 = Comment.builder()
                .comment("Test comment column2")
                .type(CommentType.COLUMN_2)
                .createdBy("test_user")
                .createdDate(LocalDateTime.now())
                .build();
        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentService.getAllCommentsForToday()).thenReturn(comments);

        // When
        ResultActions resultActions =
                mockMvc.perform(get("/")
                        .with(user("test_user")
                                .roles("MEMBER")));
        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attribute("column1Comments", hasSize(1)))
                .andExpect(model().attribute("column1Comments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("test_user")),
                                hasProperty("comment", is("Test comment column1"))
                        )
                )))
                .andExpect(model().attribute("column2Comments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("test_user")),
                                hasProperty("comment", is("Test comment column2"))
                        )
                )));

        verify(commentService, times(1)).getAllCommentsForToday();
        verifyNoMoreInteractions(commentService);
    }
}