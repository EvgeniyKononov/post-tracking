package org.example.post.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.NotFoundException;
import org.example.post.model.Post;
import org.example.post.model.Status;
import org.example.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.example.constant.Constant.NOT_FOUND_POST_ID_MSG;

@RestController
@Slf4j
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@Valid @RequestBody Post post) {
        log.info("Creating post = {}", post);
        return postService.createPost(post);
    }

    @PatchMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Post deliverPost(@PathVariable Long postId) {
        log.info("Deliver post = {}", postId);
        return postService.changePostStatus(postId, Status.DELIVERED);
    }

    @GetMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Post findPost(@PathVariable Long postId) {
        log.info("Getting post = {}", postId);
        return postService.findById(postId).orElseThrow(() -> new NotFoundException(NOT_FOUND_POST_ID_MSG));
    }
}
