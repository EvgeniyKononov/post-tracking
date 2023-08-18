package org.example.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.post.model.Post;
import org.example.post.model.Status;
import org.example.post.model.Type;
import org.example.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerMockMvcIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .type(Type.MAIL)
                .index(123456)
                .address("Russia, Moscow, Red Square, 1")
                .name("A.Petrov")
                .build();
    }

    @SneakyThrows
    @Test
    void createPost_whenInvokeWithModel_thenStatusCreatedAndReturnModel() {
        when(postService.createPost(post)).thenReturn(post);

        String result = mockMvc.perform(post("/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postService).createPost(post);
        assertEquals(objectMapper.writeValueAsString(post), result);
    }

    @SneakyThrows
    @Test
    void createPost_whenInvokeWithNullField_thenStatusBadRequest() {
        post.setIndex(null);

        mockMvc.perform(post("/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isBadRequest());

        verify(postService, never()).createPost(post);
    }

    @SneakyThrows
    @Test
    void deliverPost_whenInvokeWithModel_thenStatusCreatedAndReturnModel() {
        post.setStatus(Status.DELIVERED);
        post.setId(1L);
        when(postService.changePostStatus(post.getId(), Status.DELIVERED)).thenReturn(post);

        String result = mockMvc.perform(patch("/posts/{postId}", 1)
                        .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postService).changePostStatus(post.getId(), Status.DELIVERED);
        assertEquals(objectMapper.writeValueAsString(post), result);
    }

    @SneakyThrows
    @Test
    void deliverPost_whenInvokeWithoutPathVariables_thenStatus4xx() {
        mockMvc.perform(patch("/posts")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());

        verify(postService, never()).changePostStatus(post.getId(), Status.DELIVERED);
    }

    @SneakyThrows
    @Test
    void findPost_whenInvokeCorrectId_thenStatusOkAndReturnModel() {
        post.setId(1L);
        when(postService.findById(post.getId())).thenReturn(Optional.of(post));

        String result = mockMvc.perform(get("/posts/{postId}", 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(postService).findById(post.getId());
        assertEquals(objectMapper.writeValueAsString(post), result);
    }
}