package org.example.post.service;

import org.example.exception.ConflictException;
import org.example.exception.NotFoundException;
import org.example.office.model.Office;
import org.example.post.dao.PostRepository;
import org.example.post.model.Post;
import org.example.post.model.Status;
import org.example.post.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;
    private Post expectedPost;
    private Post actualPost;

    @BeforeEach
    void setUp() {
        expectedPost = Post.builder()
                .id(1L)
                .index(123456)
                .type(Type.MAIL)
                .name("Petrov")
                .build();
    }

    @Test
    void createPost_whenInvoke_thenReturnSavedPostAndStatusRFS() {
        when(postRepository.save(expectedPost)).thenReturn(expectedPost);

        actualPost = postService.createPost(expectedPost);

        assertEquals(expectedPost, actualPost);
        assertEquals(Status.RECEIVED_FOR_SHIPMENT, actualPost.getStatus());
        verify(postRepository).save(expectedPost);
    }

    @Test
    void findById_whenInvokeWithCorrectId_thenReturnPost() {
        Long id = 1L;
        Optional<Post> post = Optional.of(expectedPost);
        when(postRepository.findById(id)).thenReturn(post);

        actualPost = postService.findById(id).get();

        assertEquals(expectedPost, actualPost);
        verify(postRepository).findById(id);
    }

    @Test
    void changePostStatus_whenChangeStatus_thenReturnPostWithNewStatus() {
        Long id = 1L;
        when(postRepository.findById(id)).thenReturn(Optional.of(expectedPost));
        when(postRepository.save(expectedPost)).thenReturn(expectedPost);

        actualPost = postService.changePostStatus(id, Status.DELIVERED);

        assertEquals(expectedPost, actualPost);
        assertEquals(Status.DELIVERED, actualPost.getStatus());
        verify(postRepository).save(expectedPost);
    }

    @Test
    void changePostStatus_whenInvokeWithIncorrectId_thenThrowNotFoundException() {
        Long id = 2L;
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.changePostStatus(id, Status.DELIVERED));

        verify(postRepository, never()).save(expectedPost);
    }
}