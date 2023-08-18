package org.example.post.service;

import lombok.extern.slf4j.Slf4j;

import org.example.exception.NotFoundException;
import org.example.post.dao.PostRepository;
import org.example.post.model.Post;
import org.example.post.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.constant.Constant.NOT_FOUND_POST_ID_MSG;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        post.setStatus(Status.RECEIVED_FOR_SHIPMENT);
        Post savedPost = postRepository.save(post);
        log.info("post registered  = {}", savedPost);
        return savedPost;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        log.info("Searching post with Id  = {}", postId);
        return postRepository.findById(postId);
    }

    @Override
    public Post changePostStatus(Long postId, Status status) {
        Post post = findById(postId).orElseThrow(() -> new NotFoundException(NOT_FOUND_POST_ID_MSG));
        post.setStatus(status);
        Post savedPost = postRepository.save(post);
        log.info("post changed status registered  = {}", savedPost);
        return savedPost;
    }
}
